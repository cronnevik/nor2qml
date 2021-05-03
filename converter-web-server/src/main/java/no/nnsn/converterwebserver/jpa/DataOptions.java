package no.nnsn.converterwebserver.jpa;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DataOptions {
    String url;
    String text;
}
