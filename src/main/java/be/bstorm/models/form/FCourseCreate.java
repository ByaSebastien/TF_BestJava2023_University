package be.bstorm.models.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FCourseCreate(
        @NotBlank String numero,
        @NotBlank String blop,
        @NotBlank String teacherId
) {
}
