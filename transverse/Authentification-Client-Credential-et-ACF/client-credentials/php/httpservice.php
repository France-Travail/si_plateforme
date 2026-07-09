<?php

class HttpService {
    
    /*
     * Méthode permettant de se connecter en mode client crendential
     * 
     * Requête:
     * curl --location https://url --header 'Content-Type:
     * application/x-www-form-urlencoded
     * --data-urlencode client_id=clientid
     * --data-urlencode client_secret=secret
     * --data-urlencode grant_type=client_credentials
     * --data-urlencode scope=profil_accedant rechercheusager
     * 
     * Réponse:
     * {"access_token":"9d39b4ab-bd02-4319-b2d1-6b7f5b3b2fw<DSSqdsq0d",
     * "scope":"rechercheusager profil_accedant","token_type":"Bearer","expires_in":
     * 1499}
     */  
    function  authcc($urlpeam, $clientid, $clientsecret, $scope) {
        $ch = curl_init();
        try {
            
            $post_data="client_id=toot".$clientid."&client_secret=".$clientsecret."&grant_type=client_credentials&scope=".$scope;

            curl_setopt($ch, CURLOPT_URL, $urlpeam);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/x-www-form-urlencoded'));   
            curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); 
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

            $response = curl_exec($ch);
            echo "response=".$response."<br>";

            if (curl_errno($ch)) {
                echo curl_error($ch);
                return curl_error($ch);
            }
            
            $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
            if($http_code == intval(200)){
                return $response; 
            }
            else{
                return "Ressource introuvable : " . $http_code;
            }
        } catch (\Throwable $th) {
            throw $th;
        } finally {
            curl_close($ch);
        }

    }

    

}



