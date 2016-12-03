package com.generator.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mail.sander.MailSender;

public class QRCodeGenerator {
	 
    String filePath;
    int size= 250;
    String filetype = "png";
    File myFile;
    
    public QRCodeGenerator(String login){
    	this.filePath="/tmp/zdjecie.png";
    	myFile = new File(this.filePath);
    }
	
	
	public void  createQrCode(String myCodeText) throws AddressException, MessagingException{
        
        
        try{
            Map<EncodeHintType,Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            
            hintMap.put(EncodeHintType.MARGIN,1);
            hintMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
            
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix= qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size,hintMap);
            int CrunchyfyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchyfyWidth,CrunchyfyWidth,BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0,0, CrunchyfyWidth, CrunchyfyWidth);
            graphics.setColor(Color.BLACK);
            
            for(int i = 0; i < CrunchyfyWidth; i++){
                for(int j = 0; j < CrunchyfyWidth; j++){
                    if(byteMatrix.get(i, j)){
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, filetype, myFile);
          
          
        }catch (WriterException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("\n\nYou have successfully created qr code");
    }
}
