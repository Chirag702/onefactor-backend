package com.onefactor.app.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Sender {

	private Long id;
	private String firstName;
	private String lastName;
	private String phone;

}
