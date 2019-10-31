package pl.progser.api;

import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Component
public final class SheetsMapper {

    public void readValuesFromSheet(String spreadsheetId, String range) throws IOException, GeneralSecurityException {
        var sheets = GoogleAuthorize.getSheetsService(spreadsheetId);

        var response = sheets
                .spreadsheets()
                .values()
                .get(spreadsheetId, range)
                .execute();

        var values = response.getValues();

        Optional.ofNullable(values).orElseThrow(() -> new RuntimeException("Values are not given"));

        values.forEach(this::printValues);
    }

    public void writeValues(String spreadsheetId) throws IOException, GeneralSecurityException {
        var sheets = GoogleAuthorize.getSheetsService(spreadsheetId);

        var appendBody = new ValueRange()
                .setValues(List.of(
                        List.of("The", "test")
                ));

        var appendResult = sheets
                .spreadsheets()
                .values()
                .append(spreadsheetId, "testing", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    private void printValues(List<Object> value) {
        System.out.println(value);
    }
}
