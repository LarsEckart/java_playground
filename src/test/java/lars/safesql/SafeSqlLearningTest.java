package lars.safesql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.mu.safesql.SafeSql;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Learning tests documenting Google Mug's SafeSql library.
 *
 * <p>SafeSql provides compile-time checked SQL templates that make SQL injection impossible. It
 * uses {@code {placeholder}} syntax for parameters that are automatically sent through JDBC
 * PreparedStatement.
 *
 * @see <a href="https://github.com/google/mug">Google Mug on GitHub</a>
 */
class SafeSqlLearningTest {

  @Nested
  @DisplayName("Basic Value Parameters")
  class BasicParameters {

    @Test
    @DisplayName("String parameters become JDBC placeholders")
    void stringParameter() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name = {name}", "John");

      // toString() shows the parameterized SQL with ? placeholders
      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name = ?");

      // debugString() shows the values in comments for debugging
      assertThat(sql.debugString()).isEqualTo("SELECT * FROM users WHERE name = ? /* John */");
    }

    @Test
    @DisplayName("Integer parameters work the same way")
    void integerParameter() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE id = {id}", 42);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE id = ?");
      assertThat(sql.debugString()).isEqualTo("SELECT * FROM users WHERE id = ? /* 42 */");
    }

    @Test
    @DisplayName("Multiple parameters are supported")
    void multipleParameters() {
      SafeSql sql =
          SafeSql.of("SELECT * FROM users WHERE name = {name} AND age > {age}", "John", 21);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name = ? AND age > ?");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name = ? /* John */ AND age > ? /* 21 */");
    }

    @Test
    @DisplayName("Null values are handled safely")
    void nullParameter() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name = {name}", (String) null);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name = ?");
      assertThat(sql.debugString()).isEqualTo("SELECT * FROM users WHERE name = ? /* null */");
    }

    @Test
    @DisplayName("Boolean parameters are supported")
    void booleanParameter() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE active = {active}", true);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE active = ?");
      assertThat(sql.debugString()).isEqualTo("SELECT * FROM users WHERE active = ? /* true */");
    }
  }

  @Nested
  @DisplayName("IN Clause with Collections")
  class InClauseWithCollections {

    @Test
    @DisplayName("List parameters auto-expand to multiple placeholders")
    void listParameterExpandsToPlaceholders() {
      List<Integer> ids = List.of(1, 2, 3);
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE id IN ({ids})", ids);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE id IN (?, ?, ?)");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE id IN (? /* 1 */, ? /* 2 */, ? /* 3 */)");
    }

    @Test
    @DisplayName("Empty list throws exception - prevents runtime errors")
    void emptyListThrows() {
      List<Integer> empty = List.of();

      assertThatThrownBy(() -> SafeSql.of("SELECT * FROM users WHERE id IN ({ids})", empty))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("{ids} cannot be empty");
    }

    @Test
    @DisplayName("String list with quotes works for exact matches")
    void quotedStringList() {
      List<String> names = List.of("Alice", "Bob");
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name IN ('{names}')", names);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name IN (?, ?)");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name IN (? /* Alice */, ? /* Bob */)");
    }
  }

  @Nested
  @DisplayName("LIKE Clause with Automatic Escaping")
  class LikeClauseEscaping {

    @Test
    @DisplayName("LIKE with % wildcards at both ends")
    void likeWithWildcardsAtBothEnds() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name LIKE '%{term}%'", "foo");

      // SafeSql adds ESCAPE clause to handle special characters safely
      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name LIKE ? ESCAPE '^'");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name LIKE ? /* %foo% */ ESCAPE '^'");
    }

    @Test
    @DisplayName("LIKE with prefix wildcard")
    void likeWithPrefixWildcard() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name LIKE '%{term}'", "bar");

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name LIKE ? ESCAPE '^'");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name LIKE ? /* %bar */ ESCAPE '^'");
    }

    @Test
    @DisplayName("LIKE with suffix wildcard")
    void likeWithSuffixWildcard() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name LIKE '{term}%'", "baz");

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name LIKE ? ESCAPE '^'");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name LIKE ? /* baz% */ ESCAPE '^'");
    }

    @Test
    @DisplayName("Special characters in LIKE are escaped automatically")
    void specialCharsEscaped() {
      // User input contains % which should be treated literally, not as wildcard
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name LIKE '%{term}%'", "100%");

      // The % in the value is escaped with ^
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name LIKE ? /* %100^%% */ ESCAPE '^'");
    }

    @Test
    @DisplayName("Underscore wildcard is also escaped")
    void underscoreEscaped() {
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE name LIKE '_{term}_'", "test");

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name LIKE ? ESCAPE '^'");
      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE name LIKE ? /* _test_ */ ESCAPE '^'");
    }
  }

  @Nested
  @DisplayName("Identifier Parameters (Table/Column Names)")
  class IdentifierParameters {

    @Test
    @DisplayName("Backtick-quoted placeholders for column names (MySQL style)")
    void backtickQuotedIdentifier() {
      SafeSql sql = SafeSql.of("SELECT `{column}` FROM users", "email");

      // Identifier is directly inserted (not as ?), but validated
      assertThat(sql.toString()).isEqualTo("SELECT `email` FROM users");
      assertThat(sql.debugString()).isEqualTo("SELECT `email` FROM users");
    }

    @Test
    @DisplayName("Double-quoted placeholders for identifiers (ANSI SQL style)")
    void doubleQuotedIdentifier() {
      SafeSql sql = SafeSql.of("SELECT * FROM \"{table}\"", "Users");

      assertThat(sql.toString()).isEqualTo("SELECT * FROM \"Users\"");
    }

    @Test
    @DisplayName("List of column names expands properly")
    void listOfColumnNames() {
      List<String> columns = List.of("id", "name", "email");
      SafeSql sql = SafeSql.of("SELECT `{columns}` FROM users", columns);

      assertThat(sql.toString()).isEqualTo("SELECT `id`, `name`, `email` FROM users");
    }

    @Test
    @DisplayName("Identifier with illegal characters throws - prevents injection")
    void illegalIdentifierThrows() {
      // Backticks in column name would break out of quoting
      assertThatThrownBy(() -> SafeSql.of("SELECT `{column}` FROM users", "col`umn"))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("illegal");
    }

    @Test
    @DisplayName("Empty identifier throws")
    void emptyIdentifierThrows() {
      assertThatThrownBy(() -> SafeSql.of("SELECT `{column}` FROM users", ""))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("empty");
    }
  }

  @Nested
  @DisplayName("Enum as Identifier (Type-Safe Column Names)")
  class EnumIdentifiers {

    enum Column {
      ID,
      NAME,
      EMAIL;

      @Override
      public String toString() {
        return name().toLowerCase();
      }
    }

    @Test
    @DisplayName("Enum values can be used as safe identifiers")
    void enumAsIdentifier() {
      SafeSql sql = SafeSql.of("SELECT `{column}` FROM users", Column.EMAIL);

      assertThat(sql.toString()).isEqualTo("SELECT `email` FROM users");
    }

    @Test
    @DisplayName("List of enums as identifiers")
    void enumListAsIdentifiers() {
      SafeSql sql = SafeSql.of("SELECT `{columns}` FROM users", List.of(Column.ID, Column.NAME));

      assertThat(sql.toString()).isEqualTo("SELECT `id`, `name` FROM users");
    }
  }

  @Nested
  @DisplayName("Conditional Fragments with Boolean Guard")
  class ConditionalFragments {

    @Test
    @DisplayName("Boolean true includes the fragment")
    void conditionalIncluded() {
      boolean showEmail = true;
      SafeSql sql = SafeSql.of("SELECT id {show_email -> , email} FROM users", showEmail);

      assertThat(sql.toString()).isEqualTo("SELECT id , email FROM users");
    }

    @Test
    @DisplayName("Boolean false excludes the fragment")
    void conditionalExcluded() {
      boolean showEmail = false;
      SafeSql sql = SafeSql.of("SELECT id {show_email -> , email} FROM users", showEmail);

      assertThat(sql.toString()).isEqualTo("SELECT id  FROM users");
    }

    @Test
    @DisplayName("Conditional with GROUP BY clause")
    void conditionalGroupBy() {
      boolean groupByDate = true;
      SafeSql sql =
          SafeSql.of(
              """
              SELECT department_id {group_by_date -> , date}, COUNT(*)
              FROM sales
              GROUP BY department_id {group_by_date -> , date}
              """,
              groupByDate,
              groupByDate);

      assertThat(sql.toString()).contains("SELECT department_id , date, COUNT(*)");
    }
  }

  @Nested
  @DisplayName("Optional Guard - Conditional on Optional presence")
  class OptionalGuard {

    @Test
    @DisplayName("Present Optional includes fragment with value")
    void optionalPresent() {
      Optional<Integer> userId = Optional.of(42);
      SafeSql sql =
          SafeSql.of("SELECT * FROM users WHERE 1=1 {user_id? -> AND id = user_id?}", userId);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE 1=1 AND id = ?");
      assertThat(sql.debugString()).isEqualTo("SELECT * FROM users WHERE 1=1 AND id = ? /* 42 */");
    }

    @Test
    @DisplayName("Empty Optional excludes fragment entirely")
    void optionalEmpty() {
      Optional<Integer> userId = Optional.empty();
      SafeSql sql =
          SafeSql.of("SELECT * FROM users WHERE 1=1 {user_id? -> AND id = user_id?}", userId);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE 1=1 ");
    }

    @Test
    @DisplayName("Optional with LIKE pattern")
    void optionalWithLike() {
      Optional<String> searchTerm = Optional.of("john");
      SafeSql sql =
          SafeSql.of(
              "SELECT * FROM users WHERE 1=1 {term? -> AND name LIKE '%term?%'}", searchTerm);

      assertThat(sql.debugString())
          .isEqualTo("SELECT * FROM users WHERE 1=1 AND name LIKE ? /* %john% */ ESCAPE '^'");
    }

    @Test
    @DisplayName("Optional parameter can be referenced multiple times")
    void optionalReferencedMultipleTimes() {
      Optional<String> name = Optional.of("Alice");
      SafeSql sql =
          SafeSql.of(
              "SELECT {name? -> name? AS name, UPPER('name?') AS upper_name,} id FROM users", name);

      assertThat(sql.debugString())
          .contains("? /* Alice */ AS name")
          .contains("UPPER(? /* Alice */)");
    }
  }

  @Nested
  @DisplayName("Query Composition - Building Complex Queries")
  class QueryComposition {

    @Test
    @DisplayName("SafeSql can be embedded as subquery")
    void embedSubquery() {
      SafeSql whereClause = SafeSql.of("name = {name}", "John");
      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE {where}", whereClause);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE name = ?");
      assertThat(sql.debugString()).isEqualTo("SELECT * FROM users WHERE name = ? /* John */");
    }

    @Test
    @DisplayName("Multiple SafeSql fragments can be composed")
    void composeMultipleFragments() {
      SafeSql select = SafeSql.of("SELECT id, name");
      SafeSql from = SafeSql.of("FROM users");
      SafeSql where = SafeSql.of("WHERE active = {active}", true);

      SafeSql sql = SafeSql.of("{select} {from} {where}", select, from, where);

      assertThat(sql.toString()).isEqualTo("SELECT id, name FROM users WHERE active = ?");
    }

    @Test
    @DisplayName("List of SafeSql fragments")
    void listOfSafeSqlFragments() {
      List<SafeSql> conditions =
          List.of(SafeSql.of("active = {a}", true), SafeSql.of("age > {age}", 21));

      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE {conditions}", conditions);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE active = ?, age > ?");
    }
  }

  @Nested
  @DisplayName("Joining Multiple SafeSql Objects")
  class JoiningSafeSql {

    @Test
    @DisplayName("Join with AND")
    void joinWithAnd() {
      SafeSql result =
          Stream.of(
                  SafeSql.of("name = {name}", "John"),
                  SafeSql.of("active = {active}", true),
                  SafeSql.of("age > {age}", 21))
              .collect(SafeSql.joining(" AND "));

      assertThat(result.toString()).isEqualTo("name = ? AND active = ? AND age > ?");
    }

    @Test
    @DisplayName("Join with comma for SELECT columns")
    void joinWithComma() {
      SafeSql result =
          Stream.of(SafeSql.of("id"), SafeSql.of("name"), SafeSql.of("email"))
              .collect(SafeSql.joining(", "));

      assertThat(result.toString()).isEqualTo("id, name, email");
    }

    @Test
    @DisplayName("Join with OR")
    void joinWithOr() {
      SafeSql result =
          Stream.of(SafeSql.of("id = {id}", 1), SafeSql.of("id = {id}", 2))
              .collect(SafeSql.joining(" OR "));

      assertThat(result.toString()).isEqualTo("id = ? OR id = ?");
    }
  }

  @Nested
  @DisplayName("Conditional Query Construction")
  class ConditionalQueryConstruction {

    @Test
    @DisplayName("SafeSql.when() creates conditional query")
    void whenWithCondition() {
      boolean includeDeleted = false;

      SafeSql whereClause = SafeSql.when(includeDeleted, "deleted = {deleted}", true);

      assertThat(whereClause).isEqualTo(SafeSql.EMPTY);
    }

    @Test
    @DisplayName("SafeSql.when() with true condition")
    void whenWithTrueCondition() {
      boolean filterByStatus = true;

      SafeSql whereClause = SafeSql.when(filterByStatus, "status = {status}", "active");

      assertThat(whereClause.toString()).isEqualTo("status = ?");
    }

    @Test
    @DisplayName("Postfix .when() on SafeSql")
    void postfixWhen() {
      boolean applyFilter = false;

      SafeSql filter = SafeSql.of("status = {status}", "active").when(applyFilter);

      assertThat(filter).isEqualTo(SafeSql.EMPTY);
    }

    @Test
    @DisplayName("SafeSql.optionally() with Optional parameter")
    void optionallyMethod() {
      Optional<Integer> maybeId = Optional.of(42);

      SafeSql filter = SafeSql.optionally("id = {id}", maybeId);

      assertThat(filter.toString()).isEqualTo("id = ?");
    }

    @Test
    @DisplayName("SafeSql.optionally() with empty Optional")
    void optionallyWithEmpty() {
      Optional<Integer> maybeId = Optional.empty();

      SafeSql filter = SafeSql.optionally("id = {id}", maybeId);

      assertThat(filter).isEqualTo(SafeSql.EMPTY);
    }
  }

  @Nested
  @DisplayName("Safety Features - Preventing Injection")
  class SafetyFeatures {

    @Test
    @DisplayName("Question mark in SQL is forbidden - must use placeholders")
    void questionMarkForbidden() {
      assertThatThrownBy(() -> SafeSql.of("SELECT * FROM users WHERE id = ?"))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("instead of '?'");
    }

    @Test
    @DisplayName("SafeSql should not be single-quoted")
    void safeSqlNotSingleQuoted() {
      SafeSql subquery = SafeSql.of("SELECT 1");

      assertThatThrownBy(() -> SafeSql.of("SELECT '{query}'", subquery))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("should not be quoted");
    }

    @Test
    @DisplayName("SafeSql should not be backtick-quoted")
    void safeSqlNotBacktickQuoted() {
      SafeSql subquery = SafeSql.of("SELECT 1");

      assertThatThrownBy(() -> SafeSql.of("SELECT `{query}`", subquery))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("should not be backtick quoted");
    }

    @Test
    @DisplayName("Mismatched quotes are detected")
    void mismatchedQuotesDetected() {
      assertThatThrownBy(() -> SafeSql.of("SELECT * FROM `{tbl}'", "users"))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Identifier with SQL injection attempt is rejected")
    void identifierInjectionRejected() {
      // Attacker tries to break out of identifier quoting
      String maliciousInput = "users`; DROP TABLE users; --";

      assertThatThrownBy(() -> SafeSql.of("SELECT * FROM `{table}`", maliciousInput))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("illegal");
    }
  }

  @Nested
  @DisplayName("Template Method - Reusable Templates")
  class ReusableTemplates {

    @Test
    @DisplayName("Create reusable template with SafeSql.template()")
    void reusableTemplate() {
      // Define a reusable template
      var userQuery = SafeSql.template("SELECT * FROM users WHERE id = {id} AND status = {status}");

      // Use template with different values
      SafeSql sql1 = userQuery.with(/* id */ 1, /* status */ "active");
      SafeSql sql2 = userQuery.with(/* id */ 2, /* status */ "pending");

      assertThat(sql1.debugString())
          .isEqualTo("SELECT * FROM users WHERE id = ? /* 1 */ AND status = ? /* active */");
      assertThat(sql2.debugString())
          .isEqualTo("SELECT * FROM users WHERE id = ? /* 2 */ AND status = ? /* pending */");
    }
  }

  @Nested
  @DisplayName("Non-negative Literal - For Constants")
  class NonNegativeLiteral {

    @Test
    @DisplayName("Non-negative literal embeds value directly (for constants)")
    void nonNegativeLiteral() {
      // Useful when you need to embed a known-safe numeric literal
      SafeSql sql =
          SafeSql.of(
              "SELECT * FROM users WHERE id IN ({ids})",
              List.of(1, SafeSql.nonNegativeLiteral(2), 3));

      // Note: literal 2 is embedded directly, others are parameters
      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE id IN (?, 2, ?)");
    }

    @Test
    @DisplayName("Negative literal throws")
    void negativeLiteralThrows() {
      assertThatThrownBy(() -> SafeSql.nonNegativeLiteral(-1))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Nested
  @DisplayName("EMPTY Constant")
  class EmptyConstant {

    @Test
    @DisplayName("SafeSql.EMPTY represents empty SQL")
    void emptyConstant() {
      assertThat(SafeSql.EMPTY.toString()).isEmpty();
      assertThat(SafeSql.EMPTY.debugString()).isEmpty();
    }

    @Test
    @DisplayName("EMPTY is useful for conditional composition")
    void emptyInComposition() {
      boolean includeFilter = false;
      SafeSql filter = includeFilter ? SafeSql.of("AND active = {a}", true) : SafeSql.EMPTY;

      SafeSql sql = SafeSql.of("SELECT * FROM users WHERE 1=1 {filter}", filter);

      assertThat(sql.toString()).isEqualTo("SELECT * FROM users WHERE 1=1 ");
    }
  }

  @Nested
  @DisplayName("Real-World Examples")
  class RealWorldExamples {

    @Test
    @DisplayName("Dynamic search with optional filters - using composition")
    void dynamicSearchQuery() {
      // Simulating a search endpoint with optional filters
      // Using SafeSql.optionally() and composition for cleaner approach
      Optional<String> nameFilter = Optional.of("john");
      Optional<Integer> minAge = Optional.empty();
      List<String> departments = List.of("Engineering", "Sales");

      SafeSql nameClause = SafeSql.optionally("AND name LIKE '%{name}%'", nameFilter);
      SafeSql ageClause = SafeSql.optionally("AND age >= {age}", minAge);
      SafeSql deptClause = SafeSql.of("AND department IN ('{departments}')", departments);

      SafeSql query =
          SafeSql.of(
              "SELECT * FROM users WHERE 1=1 {name_filter} {age_filter} {dept_filter}",
              nameClause,
              ageClause,
              deptClause);

      assertThat(query.toString())
          .contains("AND name LIKE ?")
          .contains("AND department IN (?, ?)")
          .doesNotContain("age >=");
    }

    @Test
    @DisplayName("Building ORDER BY dynamically")
    void dynamicOrderBy() {
      String sortColumn = "name";
      boolean ascending = true;

      SafeSql orderBy =
          SafeSql.of(
              "ORDER BY `{column}` {asc -> ASC}{desc -> DESC}", sortColumn, ascending, !ascending);

      assertThat(orderBy.toString()).isEqualTo("ORDER BY `name` ASC");
    }

    @Test
    @DisplayName("Batch insert with multiple value lists")
    void batchInsertPattern() {
      // Pattern for inserting multiple rows
      List<SafeSql> valueRows =
          List.of(
              SafeSql.of("({id}, {name})", 1, "Alice"),
              SafeSql.of("({id}, {name})", 2, "Bob"),
              SafeSql.of("({id}, {name})", 3, "Charlie"));

      SafeSql insert =
          SafeSql.of(
              "INSERT INTO users (id, name) VALUES {rows}",
              valueRows.stream().collect(SafeSql.joining(", ")));

      assertThat(insert.toString())
          .isEqualTo("INSERT INTO users (id, name) VALUES (?, ?), (?, ?), (?, ?)");
    }

    @Test
    @DisplayName("Complex WHERE clause with AND/OR grouping")
    void complexWhereClause() {
      SafeSql nameCondition =
          Stream.of(SafeSql.of("first_name = {fn}", "John"), SafeSql.of("last_name = {ln}", "Doe"))
              .collect(SafeSql.joining(" AND "));

      SafeSql emailCondition = SafeSql.of("email LIKE '%{domain}%'", "@company.com");

      SafeSql whereClause =
          SafeSql.of(
              "WHERE ({name_conditions}) OR ({email_condition})", nameCondition, emailCondition);

      assertThat(whereClause.toString())
          .isEqualTo("WHERE (first_name = ? AND last_name = ?) OR (email LIKE ? ESCAPE '^')");
    }
  }
}
