package com.example.socksapp.services;

import com.example.socksapp.models.Color;
import com.example.socksapp.models.Size;
import com.example.socksapp.models.Socks;

public interface SocksService {

    Socks addSocks(Socks socks, long quantity);

    Socks editSocks(Socks socks, long quantity);

    long getSocksNumByParam(Color color, Size size, int cottonMin, int cottonMax);

    boolean deleteSocks(Socks socks, long quantity);
}
