package com.example.socksapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    private Color color;
    private Size sizeOfSocks;
    private int cottonPart;
    private int quantity;
    private boolean InStock;
}