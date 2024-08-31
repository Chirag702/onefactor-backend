package com.onefactor.app.Response;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckValueResponse {
    private boolean isPresent;
    private String field;
}

