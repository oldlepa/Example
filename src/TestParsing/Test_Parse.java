package TestParsing;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public class Test_Parse {

 public static void main(String[] args){
	 
		String recvbuff = "userdetails.token.id=AQIC5wM2LY4SfcwmBNWY3TeDPd_Xe7wAKTk6eKQ6sPnC-OQ.*AAJTSQACMDMAAlNLABM1MTA0Njk2NTgwOTE1MDQyMTE1AAJTMQACMDQ.*userdetails.role=id=gnt-conf-idp.carrefour.com,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.role=id=authentication_all,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.role=id=grant_base_users_rights,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.role=id=PRTG_GNT_IPServices_operations,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.role=id=DIO_DW_Gestion_d_identites,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.role=id=admin-ipservices,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.role=id=surveys_sps,ou=group,o=prod,ou=services,dc=openam,dc=forgerock,dc=orguserdetails.attribute.name=LDAPFR_UIDuserdetails.attribute.value=FR732867userdetails.attribute.name=gntguiduserdetails.attribute.value=0005uvc7etm91hjc";
		
		String[] parts = recvbuff.split("userdetails.attribute.name=");
		
		Map<String,String> map = new HashMap<>();
		
		for(int i=0;i<parts.length;i++){
			if(parts[i].contains("userdetails.attribute.value=")){
				String[] part2 = parts[i].split("userdetails.attribute.value=");
				map.put(part2[0], part2[1]);
			}
			
		}
		
		//String str3New = map.get("gntguid");
		String str3New = "";
		if(map.containsKey("LDAPFR_UID")){
			str3New = map.get("LDAPFR_UID");
			
		}else{
			str3New = map.get("gntguid");
			
		}
	    String last = recvbuff.substring(recvbuff.lastIndexOf("=")+1, recvbuff.length());
		
		//String str = parts[2].trim();
		
		System.out.println("la valeur recupere est :"+last);
 }
	
}
