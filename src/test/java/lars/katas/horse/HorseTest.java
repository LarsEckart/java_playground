package lars.katas.horse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

// @RunWith(FasterTestCommitRevertRunner.class)
public class HorseTest {

  @UseReporter(ClipboardReporter.class)
  @Test
  public void FilterSortPaginate_No_Filters_No_Sorting_No_Pagination() throws IOException {
    // Arrange - this is data from another service or database
    List<String> headers = SampleHorseData.GetSampleHeaders();
    List<List<Object>> tableData = SampleHorseData.GetSampleTableData();

    // These objects describe the query we got from the front end
    List<FilterMetadata> filters = Collections.emptyList();
    Optional<SortMetadata> sortMetadata = Optional.empty();
    PaginationMetadata paginationMetadata = new PaginationMetadata(0, 9);

    // Act
    PaginatedTable table =
        Horse.FilterSortPaginateTable(
            headers, tableData, filters, sortMetadata, paginationMetadata);

    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    String json = mapper.writeValueAsString(table);

    Approvals.verify(json);
  }
}
