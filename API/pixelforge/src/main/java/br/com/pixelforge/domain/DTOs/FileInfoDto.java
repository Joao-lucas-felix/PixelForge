package br.com.pixelforge.domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDto {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long fileSize;
}
