package lars.katas.horse;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
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

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<PaginatedTable> jsonAdapter = moshi.adapter(PaginatedTable.class);

    String json = jsonAdapter.toJson(table);
    // Assert the data to be sent to the front end
    // TODO: get moshi support for ApprovalTests?
    Approvals.verify(json);
  }
}
