package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class RePasswordDTO {

    private Long id;

    private String oldPassword;

    private String newPassword;

    private String againPassword;

}
