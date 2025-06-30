package com.example.image.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Image {
    private Long id;
    private byte[] image;

}