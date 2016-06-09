/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.zcat.mergecontacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author zeus
 */
public class MergeContacts {
    private static TreeMap<Integer, TreeMap<String, LinkedList<String>>> contactList;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*byte[] s1 = "Casper".getBytes(), s2 = "Zeus".getBytes();
        int x = 0;
        for(int i = 0; i < s1.length; i++) {
            System.out.println("s1 at " + i + " " + Integer.toBinaryString(s1[i]));
            System.out.println("x " + Integer.toBinaryString(x));
            x ^= s1[i];
        }
        System.out.println(Integer.toBinaryString(x));
        for(int i = 0; i < s2.length; i++) {
            System.out.println(Integer.toBinaryString(x));
            x ^= s2[i];
        }
        name prefix
        name
        middle
        last
        name suffix
        System.out.println(Integer.toBinaryString(x));*/
        contactList = new TreeMap();
        android();
        //display();
        sameName();
    }
    
    private static void android() {
        String vcfFile = "/home/zeus/Contacts/contacts.vcf";
        BufferedReader br = null;
        String line = "";
        int xor = 0;
        TreeMap contact = null;
        try {
            br = new BufferedReader(new FileReader(vcfFile));
            while ((line = br.readLine()) != null) {
                if(line.contains("PREF;")) 
                    line = line.replace("PREF;", "");
                else if(line.equals("BEGIN:VCARD"))
                    contact = new TreeMap();
                else if(line.equals("END:VCARD"))
                    contactList.put(xor, contact);
                else if(line.startsWith("N:")&& !line.equals("N:;;;;")) {
                    String name[] = line.substring(2).split(";");
                    xor = 0;
                    for(int i = 0; i < name.length; i++) {
                        if(!name[i].isEmpty()) {
                            if(i==0) 
                                contact.put("LAST", name[0].trim());
                            if(i==1) 
                                contact.put("FIRST", name[1].trim());
                            if(i==2) 
                                contact.put("MIDDLE", name[2].trim());
                            if(i==3) 
                                contact.put("PREFIX", name[3].trim());
                            if(i==4) 
                                contact.put("SUFFIX", name[4].trim());
                            byte word[] = name[i].getBytes();
                            for(int j = 0; j < word.length; j++) {
                               xor ^= word[j];
                            }
                        }
                    }
                } else if(line.startsWith("TEL")) {
                    if(line.contains("CELL:")) 
                        contact.put("TEL;CELL:", line.replace("TEL;TYPE=CELL:", ""));
                    else if(line.contains("WORK:"))
                        contact.put("TEL;WORK:", line.substring(line.indexOf("TYPE=WORK:")+10));
                    else if(line.contains("HOME:"))
                        contact.put("TEL;HOME:", line.substring(line.indexOf("TYPE=HOME:")+10));
                    else contact.put("TEL:", line.substring(4));
                } else if(line.contains("EMAIL"))
                    contact.put("EMAIL", line.substring(line.lastIndexOf(":")+1));
                //else System.out.println(line);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
	} finally {
            if (br != null) {
            	try {
                    br.close();
		} catch (IOException e) {
                    e.printStackTrace();
		}
            }
	}
    }
    
    private static void google() {
        String csvFile = "/home/zeus/Contacts/google.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = new String(br.readLine().getBytes("UTF8"))) != null) {
	        // use comma as separator
		String[] country = line.split(cvsSplitBy);
		for(int i = 0; i < country.length; i++) {
                    System.out.print(country[i] + " ");
                }
                System.out.println("\n");
            }

	} catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
	} finally {
            if (br != null) {
            	try {
                    br.close();
		} catch (IOException e) {
                    e.printStackTrace();
		}
            }
	}
    }

    private static void display() {
        for(Map.Entry<Integer, TreeMap<String, LinkedList<String>>> entry : contactList.entrySet()) {
            int key = entry.getKey();
            TreeMap value = entry.getValue();
            System.out.println(key + " => " + value);
        }
    }

    private static void sameName() {
        for(Map.Entry<Integer, TreeMap<String, LinkedList<String>>> entry : contactList.entrySet()) {
            int key = entry.getKey();
            LinkedList l = null;
            String first="", last = "";
            if(entry.getValue().containsKey("FIRST"))
                first = entry.getValue().get("FIRST").toString();
            first = l.toString();
            if(entry.getValue().containsKey("LAST"))
                l = entry.getValue().get("LAST");
            last = l.toString();
            System.out.println(first+last);
            for(Map.Entry<Integer, TreeMap<String, LinkedList<String>>> sub : contactList.entrySet()) {
                int subKey = sub.getKey();
                if(subKey != key) {
                    
                }
            }
        }
    }
    
}
