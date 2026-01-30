<?php

class HttpService {
    
    //--------------------------------------------------------------------------------------------------------------------------------------
    // Cette méthode permet d'éffectuer la validation du code retour du flow
    // Validation du code de retour se fait à la receprion du flow retour
    // http://localhost:8080/auth/auth.html?code=XXXXXX&scope=XXXX&&iss=XXXX&client_id=XXXX 
    //  
    //  Appel à effectué pour valider le code retour:
    //  curl --location url --header 'Content-Type: application/x-www-form-urlencoded 
    // --data-urlencode client_id=XXXXXXX 
    // --data-urlencode client_secret=XXXXXXXXX 
    // --data-urlencode grant_type=authorization_code 
    // --data-urlencode code=XXXXXXXXX 
    // --data-urlencode redirect_uri==http://localhost:8080/auth/auth.html
    //
    // Résultat de la Requête
    // {"access_token":"XXXXXXX","refresh_token":"XXXXXXXX","scope":"openid","id_token":"XXXXXXXXX","token_type":"Bearer","expires_in":1499}
    // 
    //----------------------------------------------------------------------------------------------------------------------------------------
    function  authagentcode($urlpeam, $clientid, $clientsecret, $code) {

        $ch = curl_init();
        try {
            
            $post_data="client_id=toot".$clientid."&client_secret=".$clientsecret."&grant_type=authorization_code&code=".$code;

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



