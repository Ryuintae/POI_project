package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    private Long image_id;
    private Long user_id;
    private LocalDateTime save_date;
    private String file_name;
    private String file_extention;
    private Long file_size;
    private String file_path;
}
