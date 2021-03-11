package whiteCollar.service;

import whiteCollar.entity.Picture;

import java.util.List;

/**
 * Interface de la capa Service
 *
 */
public interface IPictureService {

    //List<Picture> listPictures(List<Picture> pictures); //List All Pictures By Shop

    Picture savePicture(Picture picture); //Save a picture in Shop CREATE

    void firePictures(List<Picture> pictures); //Delete All Pictures By Shop


}
