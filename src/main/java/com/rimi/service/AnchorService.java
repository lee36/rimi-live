package com.rimi.service;

import com.rimi.model.Anchor;
import com.rimi.model.User;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public interface AnchorService {
    public Anchor regist(Anchor anchor);
    public boolean saveAnchorAndCreateLiveRoom(Anchor anchor);
    public byte[] transPartToBytes(Part part) throws IOException;
    public boolean writeFileToPath(byte[] files, File parent, String fileName) throws IOException;
    public Anchor findByEmail(String email);
}
