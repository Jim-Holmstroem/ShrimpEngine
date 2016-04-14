// spara/ladda information i en fil

import javax.microedition.rms.RecordStore;
import java.lang.String;
import java.io.*;

public class ReadWrite{ 

public RecordStore rms;//objectet //<--måste man göra en ny rms varje gång kanske räcker om den är static??? :P
public String storeName;//filnamnet
private String fileType = "int";

//olika modes för att skriva och läsa i RMS (måste väljas vid initsieringen)
private int rmsMode = 0;
public static final int ONE_RECORD = 1; //få den att funka olika vid olika modes precis som i PHP (typ)
                        

    public ReadWrite(String name){
        storeName = name;
        try{//försök att öppna upp den tillfrågade filen, eller skapa den om den inte finns
            rms =  RecordStore.openRecordStore(name,true);
        }catch(javax.microedition.rms.RecordStoreException ex){}//om error

    }
    
    
    public boolean writeRecord(int id, long data){//skriv en long till filen
       try{
            byte[] bytes = toByteArray(data);
            rms.setRecord(id,bytes,0,bytes.length);
            
       }catch(javax.microedition.rms.RecordStoreException e){return false;}//
       return true;
    }
        
    public boolean newRecord(long data){//skapa en ny fil
        try{
            byte[] bytes = toByteArray(data);//gör om int'n till en byte.array
            rms.addRecord(bytes,0,bytes.length);//skriver in bytearrayen i filen
        }catch(javax.microedition.rms.RecordStoreException e){return false;}//returna falskt om error
      return true;
    }

    
    public void deleteRecord(int id){//tar bort fil
        try{
            if (rms != null) //om  filen finns
                rms.deleteRecord(id);//ta bort den
        }catch(javax.microedition.rms.RecordStoreException e){System.out.println("Exception, deleteRecord():" + storeName + "." + id);}//
    }
     
    private byte[] toByteArray(long data){
      try{  
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //bytearray-ström
        DataOutputStream os = new DataOutputStream(baos);//skapa en data-ström av bytedata-strömmen
        
        os.writeLong(data);//skriv till strömmen

        return baos.toByteArray();//returnera bytedataströmmen som en lista
      }catch(IOException ioe){//om det skulle bli fel
        System.err.println(ioe.getMessage());
        return null;
      }
    }
    
    // given its record identifier
public long readRecord(int id)//läs från filen
{
long data = 0;
    try{  
      try{  
        try{
        try{
        try{
        
            byte[] bytes = rms.getRecord(id);

            DataInputStream is = new DataInputStream(new ByteArrayInputStream(bytes));
            
            data = is.readLong();
       
        }catch(javax.microedition.rms.RecordStoreNotFoundException e){return -1;}
        }catch(javax.microedition.rms.RecordStoreNotOpenException e){return -1;}    
        }catch(javax.microedition.rms.InvalidRecordIDException e){return -1;}
     }catch(javax.microedition.rms.RecordStoreException e){return -1;}
    }catch(IOException ioe){return -1;}
return data;
}
    
    
    public void closeRecordStore(){//stäng filen
        try{
            rms.closeRecordStore();
        }catch(javax.microedition.rms.RecordStoreException e){}//
    
    }
   public int length(){
        try{
           return rms.getNumRecords();
        }catch(javax.microedition.rms.RecordStoreException e){}//
    return -1;
   }
   
   public boolean resetStores(){//tar bort alla elemnteten men vet inte om det blir nå fel med id-numrerna :P
           String[] nameList = rms.listRecordStores();
            
            try{
                for(int i = 0; nameList[i] != null;i++){
                    deleteStore(nameList[i]);
                }
            }catch(Exception e){}
      return true;
   }
   
   public boolean deleteStore(String name){
        try{
            rms.deleteRecordStore(name);
        }catch(javax.microedition.rms.RecordStoreException ex){return false;}
        return true;
   }
   
   public int getSize(){
       try{
           return rms.getSize();
       }catch(javax.microedition.rms.RecordStoreException ex){return -1;}
   } 
   
}
