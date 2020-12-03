package com.reply.videostreaming.domain;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Payment Entity
 * @author sraamasubbu
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Payment implements Serializable {

    @NotBlank
    @Pattern(regexp = "(^\\d{16}$)")
    private String creditCardNo;

    @Min(1)
    @Max(999)
    private int amount;
}
