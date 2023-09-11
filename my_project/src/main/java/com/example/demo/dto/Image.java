package com.example.demo.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    private int image_id;
    //private int user_id;
    private int route_id;
    private int poi_num;
    private String file_name;
    private String file_extention;
    private Long file_size;
    private String file_path;
}
