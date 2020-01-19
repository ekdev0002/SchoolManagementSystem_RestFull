<?php 

    use Psr\Http\Message\ServerRequestInterface as Request;
    use Psr\Http\Message\ResponseInterface as Response;

    require '../vendor/autoload.php';
	
	    function xml_encode ($array, $xmlRootElement=null) {
        $string_xml = "";
        if ( $xmlRootElement != null ) $string_xml = "<$xmlRootElement>";
        foreach ($array as $element => $content) {
            $string_xml = $string_xml."<$element>";
            $string_xml = $string_xml.$content;
            $string_xml = $string_xml."</$element>";
        }
        if ( $xmlRootElement !=null ) $string_xml = $string_xml."</$xmlRootElement>";
        return $string_xml;
    }

    function xml_encode_array ($array, $xmlElement) {
        $string_xml = "";
        foreach ($array as $element) {
            $string_xml = $string_xml.xml_encode($element, $xmlElement);
        }
        return $string_xml;
    }

    function message ($status,$message,$object=null) {
        if ($object == null) {
            return array("status" => $status, "message" => $message);
        } else {
            return array("status" => $status, "message" => $message, "etudiants" => $object);
        }        
    }


    $app = new \Slim\App;

    /**
     * route - CREATE - add new eleve - POST method
     */
    $app->post
    (
        '/api/eleve', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $idClasse = $params['idClasse'];
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];

                $sql = "insert into T_eleves (nom,prenom,genre,idClasse,login,password,email,telephone,path) values (:nom,:prenom,:genre,:idClasse,:login,:password,:email,:telephone,:path)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':genre', $genre);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':login', $login);
                $statement->bindParam(':password', $password);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
                $statement->bindParam(':path', $path);
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "L'éleve a été ajouté avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * route - READ - get eleve by id - GET method
     */
    $app->get
    (
        '/api/eleve/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_eleves where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $eleve = $statement->fetch(PDO::FETCH_OBJ);
                    $eleve_array = array( 
                        "id" => $eleve->id, 
                        "nom" => $eleve->nom,
                        "prenom" => $eleve->prenom,
                        "genre" => $eleve->genre,
                        "telephone" => $eleve->telephone,
                        "idClasse" => $eleve->idClasse,
                        "login" => $eleve->login,
                        "password" => $eleve->password,
                        "email" => $eleve->email,              
                        "path" => $eleve->path);           
                    $body->write(xml_encode($eleve_array, "eleve"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The eleve with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get eleve by login and password - GET method
     */
    $app->get
    (
        '/api/eleve/{login}/{password}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $login = $request->getAttribute('login');                
                $password = $request->getAttribute('password');                

                $sql = "select * from t_eleves where login = :login and password = :password ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':login' => $login,':password' => $password));
                if ($statement->rowCount()) {
                    $eleve = $statement->fetch(PDO::FETCH_OBJ);
                    $eleve_array = array( 
                        "id" => $eleve->id, 
                        "nom" => $eleve->nom,
                        "prenom" => $eleve->prenom,
                        "genre" => $eleve->genre,
                        "telephone" => $eleve->telephone,
                        "idClasse" => $eleve->idClasse,
                        "login" => $eleve->login,
                        "password" => $eleve->password,
                        "email" => $eleve->email,              
                        "path" => $eleve->path);
                    $body->write(xml_encode($eleve_array, "eleve"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "L'étudiant avec login = '".$login."' et password ='".$password."' n'a pas été trouvé ou a été supprimé."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get all eleves - GET method
     */
    $app->get
    (
        '/api/eleves', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select e.id,e.nom,e.prenom,e.telephone,e.email,e.genre,e.login,e.password,e.path,e.idClasse,c.libelle as libelleClasse From T_Eleves e JOIN T_Classes c On e.idClasse = c.id";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $eleveList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $eleve_str = xml_encode_array ($eleveList, "eleve");
                    $body->write(xml_encode(array("eleveList" => $eleve_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucun élève n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - READ - get all eleves by Classe id - GET method
     */
    $app->get
    (
        '/api/elevesbyclasse/{idClasse}', 
        function (Request $request, Response $old_response) {
            try {
				$idClasse = $request->getAttribute('idClasse'); 
                $sql = "Select * From T_Eleves where idClasse=:idClasse";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
				
                $statement = $db_connection->prepare($sql);
				$statement->bindParam(':idClasse', $idClasse);
				$statement->execute();
				
                if ($statement->rowCount()) {
                    $eleveList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $eleve_str = xml_encode_array ($eleveList, "eleve");
                    $body->write(xml_encode(array("eleveList" => $eleve_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucun élève n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - READ - get all eleves by Enseignant id - GET method
     */
    $app->get
    (
        '/api/elevesbyenseignant/{idEnseignant}', 
        function (Request $request, Response $old_response) {
            try {
				$idEnseignant = $request->getAttribute('idEnseignant'); 
                $sql = "select * from t_eleves where idClasse in (select id from t_classes where id in (select idClasse from t_enseignants_classes_modules where idEnseignant=:idEnseignant))";
				
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

				$statement = $db_connection->prepare($sql);				
				$statement->bindParam(':idEnseignant', $idEnseignant);               
				$statement->execute();
				
                if ($statement->rowCount()) {
                    $eleveList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $eleve_str = xml_encode_array ($eleveList, "eleve");
                    $body->write(xml_encode(array("eleveList" => $eleve_str)));
                } else {
                    $body->write(xml_encode(message('KO', "Aucun élève n'a été enregistré pour le moment."),"response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

	
	
    /**
     * route - UPDATE - update an eleve by id - PUT method
     */
    $app->put
    (
        '/api/eleve/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $idClasse = $params['idClasse'];
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];

                $sql = "update T_Eleves set nom = :nom, prenom = :prenom, genre = :genre,idClasse= :idClasse,login = :login,password = :password
                ,email=:email,telephone=:telephone,path=:path where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':genre', $genre);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':login', $login);
                $statement->bindParam(':password', $password);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
                $statement->bindParam(':path', $path);
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "L'étudiant a été mis à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.")));
            }

            return $response;
        }
    );

    /**
     * route - DELETE - delete an eleve by id - DELETE method
     */
    $app->delete
    (
        '/api/eleve/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_eleves where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The eleve has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );

    /** 
     * Module CREATE a Module - POST METHOD
     * 
     */
    $app->post
    (
        '/api/module', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $coefficient = $params['coefficient'];
                $libelle = $params['libelle'];
                $description = $params['description'];
                
                $sql = "insert into T_Modules (coefficient,libelle,description) values (:coefficient,:libelle,:description)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':coefficient', $coefficient);
                $statement->bindParam(':libelle', $libelle);
                $statement->bindParam(':description', $description);
               
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le module a été ajouté avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * route - READ - get module by id - GET method
     */
    $app->get
    (
        '/api/module/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_Modules where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $module = $statement->fetch(PDO::FETCH_OBJ);
                    $module_array = array( 
                        "id" => $module->id, 
                        "coefficient" => $module->coefficient,
                        "libelle" => $module->libelle,
                        "description" => $module->description);           
                    $body->write(xml_encode($module_array, "module"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The module with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get module by enseignant signed in- GET method
     */
    $app->get
    (
        '/api/module/enseignant/{idEnseignant}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('idEnseignant');                
                            

                $sql = "select m.id,m.coefficient,m.libelle,c.libelle as libelleClasse from t_modules m, t_enseignants_classes_modules e,t_classes c where m.id=e.idModule and e.idClasse=c.id and e.idEns=:id;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                
                if ($statement->rowCount()) {
                    $moduleList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $module_str = xml_encode_array ($moduleList, "module");
                    $body->write(xml_encode(array("moduleList" => $module_str)));
                
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les modules donnés par l'enseignant avec id = '".$id."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );

	
    /**
     * route - READ - get module by eleve and classe - GET method
     */
    $app->get
    (
        '/api/module/eleve/{idEleve}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $idEleve = $request->getAttribute('idEleve');                
                            

                $sql = "Select * From T_Modules where id in (select idModule from t_enseignants_classes_modules where idClasse in(select idClasse from t_eleves where id=:idEleve));";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':idEleve' => $idEleve));
                
                if ($statement->rowCount()) {
                    $moduleList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $module_str = xml_encode_array ($moduleList, "module");
                    $body->write(xml_encode(array("moduleList" => $module_str)));
                
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les modules suivi par l'eleve avec id = '".$idEleve."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );
	
	
	
	
    /**
     * route - READ - get all modules - GET method
     */
    $app->get
    (
        '/api/modules', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_modules";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $moduleList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $module_str = xml_encode_array ($moduleList, "module");
                    $body->write(xml_encode(array("moduleList" => $module_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucun cours n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a module by id for a classe and an enseignant - PUT method
     */
    $app->put
    (
        '/api/module/{idClasse}/{idEns}', 
        function (Request $request, Response $old_response) {
            try {
				
				$idClasse = $request->getAttribute('idClasse');
				$idEns = $request->getAttribute('idEns');
              
                $params = $request->getQueryParams();
                $id = $params['id'];
                $coefficient = $params['coefficient'];
                $libelle = $params['libelle'];
                $description = $params['description'];

                $sql = "update t_modules set coefficient=:coefficient, libelle=:libelle, description=:description where id=:id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id',$id);
                $statement->bindParam(':libelle',$libelle);
                $statement->bindParam(':coefficient',$coefficient);
                $statement->bindParam(':description',$description);
                
                $statement->execute();

				
				$sql1 = "INSERT INTO t_enseignants_classes_modules (IdModule,IdEns,IdClasse) VALUES (:id,:idEnseignant,:idClasse) ON DUPLICATE KEY UPDATE IdModule=:id , IdEns=:idEnseignant, IdClasse=:idClasse";
                $statement1 = $db_connection->prepare($sql1);
                $statement1->bindParam(':id',$id);
                $statement1->bindParam(':idEnseignant',$idEns);
                $statement1->bindParam(':idClasse', $idClasse);
               
               
                $statement1->execute();

				
                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le module a été mis à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO',  $exception->getMessage())));
            }

            return $response;
        }
    );
 
    /**
     * route - DELETE - delete a module by id - DELETE method
     */

    $app->delete
    (
        '/api/module/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_modules where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The module has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );


	/** 
     * Module CREATE a module - POST Method
     * 
     */
    $app->post
    (
        '/api/module/setenseignant/{idEnseignant}/{idClasse}',
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                $idEnseignant = $request->getAttribute('idEnseignant');
				$idClasse = $request->getAttribute('idClasse');
                $id = $params['id'];
                        
                

				$sql = "update t_enseignants_classes_modules set IdModule=:id where IdEns=:idEnseignant and IdClasse=:idClasse";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':idEnseignant', $idEnseignant);
                $statement->bindParam(':idClasse', $idClasse);
               
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le module a été ajouté avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );
	
	/** 
     * Classe- CREATE a classe - POST Method
     * 
     */

    $app->post
    (
        '/api/classe', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $libelle = $params['libelle'];
                $description = $params['description'];
               
                

                $sql = "insert into T_classes (libelle,description) values (:libelle,:description)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':libelle', $libelle);
                $statement->bindParam(':description', $description);
                
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La classe a été enregistrée avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * route - READ - get classe by id - GET method
     */
    $app->get
    (
        '/api/classe/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_classes where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $classe = $statement->fetch(PDO::FETCH_OBJ);
                    $classes_array = array( 
                        "id" => $classe->id, 
                        "libelle" => $classe->libelle,           
                        "description" => $classe->description);           
                    $body->write(xml_encode($classes_array, "classe"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The class with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get classe by libelle- GET method
     */
    $app->get
    (
        '/api/classe/libelle/{libelle}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $libelle = $request->getAttribute('libelle');
                
               // $idCandidature = $params['idCandidature'];
                //$libelle = $params['libelle'];
                //$url = $request->getAttribute('url');                      

                $sql = "select * from t_classes where libelle = :libelle ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':libelle' => $libelle));
                if ($statement->rowCount()) {
                    $classe = $statement->fetch(PDO::FETCH_OBJ);
                    $classes_array = array( 
                        "id" => $classe->id, 
                        "libelle" => $classe->libelle,           
                        "description" => $classe->description);           
                    $body->write(xml_encode($classes_array, "classe"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "La classe'".$libelle."'  n'a pas été trouvé ou a été supprimé."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );

    /**
     * route - READ - get all classes - GET method
     */
    $app->get
    (
        '/api/classes', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_classes";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $classeList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $classes_str = xml_encode_array ($classeList, "classe");
                    $body->write(xml_encode(array("classeList" => $classes_str)));
                } else {
                    $body->write(xml_encode(message('KO', "Aucune classe n'a été enregistrée pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

 

    /**
     * route - READ - get all classes - GET method
     */
    $app->get
    (
        '/api/classe/enseignant/{id}', 
        function (Request $request, Response $old_response) {
            try {
                $id = $request->getAttribute('id');

                $sql = "select * from t_classes where Id in (select idClasse from t_enseignants_classes_modules where idEns=:id group by (idClasse))";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $classes = $statement->fetchAll(PDO::FETCH_OBJ);
                    $classes_str = xml_encode_array ($classes, "classe");
                    $body->write(xml_encode(array("classeList" => $classes_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucune classe n'a été enregistrée pour le moment pour ce enseignant.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a classe by id - PUT method
     */
    $app->put
    (
        '/api/classe/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                $description = $params['description'];
                $libelle = $params['libelle'];

                $sql = "update t_classes set libelle = :libelle, description = :description where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':description', $description);
                $statement->bindParam(':libelle', $libelle);
               
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La classe a été mise à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO',  $exception->getMessage()), "response"));
            }

            return $response;
        }
    );

    /**
     * route - DELETE - delete a classe by id - DELETE method
     */
    $app->delete
    (
        '/api/classe/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_classes where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The class has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.".$exception->getMessage())));
            }
            return $response;
        }
    );

    /** 
     * Classe- CREATE a Note - POST Method
     * 
     */
    $app->post
    (
        '/api/note', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $idDevoir = $params['idDevoir'];
                $note = $params['note'];
                $coef = $params['coef'];
                $idEleve = $params['idEleve'];
               
                
                

                $sql = "insert into T_note (idDevoirs,note,coef,idEleve) values (:idDevoir,:note,:coef,:idEleve)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':idEleve', $idEleve);
                $statement->bindParam(':note', $note);
                $statement->bindParam(':coef', $coef);
                $statement->bindParam(':idDevoir', $idDevoir);
                
               
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La note  a été ajoutée avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * route - READ - get note by id - GET method
     */
    $app->get
    (
        '/api/note/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_note where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $note = $statement->fetch(PDO::FETCH_OBJ);
                    $note_array = array( 
                        "id" => $note->id, 
                        "note" => $note->note,
                        "coef" => $note->coef,
                        "idEleve" => $note->idEleve,
                        "idDevoir" => $note->idDevoirs);           
                    $body->write(xml_encode($note_array, "note"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The mark with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );



    /**
     * route - READ - get note by enseignant  GET method
     */
    $app->get
    (
        '/api/note/enseignant/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                
                            

                $sql = "select * from T_Note where Id in (select IdNote from t_enseignants_note where idEnseignant=:id)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                
                if ($statement->rowCount()) {
                    $notes = $statement->fetchAll(PDO::FETCH_OBJ);
                    $notes_str = xml_encode_array ($notes, "notes");
                    $body->write(xml_encode(array("notes" => $notes_str)));
                
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les notes données par l'enseignant avec id = '".$id."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));
            }
            
            return $response;
        }
    );

	/**
     * route - READ - get note by devoir  GET method
     */
    $app->get
    (
        '/api/note/devoir/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $idDevoir = $request->getAttribute('id');                
                            

                $sql = "select * from T_Note where idDevoirs=:idDevoir";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':idDevoir' => $idDevoir));
                
                if ($statement->rowCount()) {
                    $noteList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $notes_str = xml_encode_array ($noteList, "note");
                    $body->write(xml_encode(array("noteList" => $notes_str)));
                
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les notes données par devoirt avec id = '".$idDevoir."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));
            }
            
            return $response;
        }
    );




	/**
     * route - READ - get note by devoir and eleve  GET method
     */
    $app->get
    (
        '/api/note/{idDevoir}/{idEleve}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $idDevoir = $request->getAttribute('idDevoir');
				$idEleve = $request->getAttribute('idEleve');
                            

                $sql = "select * from T_Note where idDevoirs=:idDevoir and idEleve=:idEleve";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':idDevoir' => $idDevoir,
										  ':idEleve' => $idEleve));
                
                if ($statement->rowCount()) {
                    $noteList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $notes_str = xml_encode_array ($noteList, "");
                    $body->write(xml_encode(array("note" => $notes_str)));
                
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les notes données par devoirt avec id = '".$idDevoir."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));
            }
            
            return $response;
        }
    );




	
    /**
     * route - READ - get all notes - GET method
     */
    $app->get
    (
        '/api/notes', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_note";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $notes = $statement->fetchAll(PDO::FETCH_OBJ);
                    $notes_str = xml_encode_array ($notes, "notes");
                    $body->write(xml_encode(array("notes" => $notes_str)));
                
                } else {
                    $body->write(json_encode(message('KO', "Aucune note n'a été enregistrée pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a note by id - PUT method
     */
    $app->put
    (
        '/api/note/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                
                $idDevoir = $params['idDevoir'];
                $note = $params['note'];
                $coef = $params['coef'];
                $idEleve = $params['idEleve'];

                $sql = "Update T_Note Set idDevoirs = :idDevoir,Note = :note, coef = :coef, idEleve = :idEleve Where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':idDevoir', $idDevoir);
                $statement->bindParam(':note', $note);
                $statement->bindParam(':idEleve', $idEleve);
                $statement->bindParam(':coef', $coef);
                
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La note a été mise à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO',  $exception->getMessage())));
            }

            return $response;
        }
    );
 
 
 

    /**
     * route - DELETE - delete a note by id - DELETE method
     */

    $app->delete
    (
        '/api/note/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_note where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The note has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );
	
	
	
	/** 
     * Classe- CREATE a cours - POST Method
     * 
     */
    $app->post
    (
        '/api/cours', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $idModule = $params['idModule'];
                $idClasse = $params['idClasse'];
                $description = $params['description'];
                $dateHeure = $params['dateHeure'];
                $duree = $params['duree'];
                

                $sql = "insert into T_cours (IdModule,idClasse,description,dateHeure,duree) values (:idModule,:idClasse,:description,:dateHeure,:duree)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':idModule', $idModule);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':description', $description);
                $statement->bindParam(':dateHeure', $dateHeure);
                $statement->bindParam(':duree', $duree);
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le cours a été ajouté avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /** 
     * Classe- CREATE a classe for an enseignant- POST Method
     * 
     */
    $app->post
    (
        '/api/cours/{idEns}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $idModule = $params['idModule'];
                $idClasse = $params['idClasse'];
                $description = $params['description'];
                $dateHeure = $params['dateHeure'];
                $duree = $params['duree'];
				
				$idEns = $request->getAttribute('idEns');
				$idCours = "";
                

                $sql = "insert into T_cours (IdModule,idClasse,description,dateHeure,duree) values (:idModule,:idClasse,:description,:dateHeure,:duree)";

				$sql1 = "Select id From t_Cours Where idModule = :idModule and idClasse = :idClasse and description = :description and dateHeure = :dateHeure and duree = :duree";
				
				$sql2 = "Insert Into T_Enseignants_Cours (idEnseignant,idCours) values (:idEnseignant,:idCours)";
				
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
			
                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':idModule', $idModule);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':description', $description);
                $statement->bindParam(':dateHeure', $dateHeure);
                $statement->bindParam(':duree', $duree);               
                $statement->execute();

                $statement1 = $db_connection->prepare($sql1);
                $statement1->execute(array( 
                        "idModule" => $idModule,
                        "idClasse" => $idClasse,
                        "description" => $description,             
                        "dateHeure" => $dateHeure,             
                        "duree" => $duree));
						
                if ($statement1->rowCount()) {
                    $idCours= $statement1->fetch(PDO::FETCH_OBJ);
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':idEnseignant', $idEns);
					$statement2->bindParam(':idCours', $idCours->id);
					$statement2->execute();
					
					$db_access->releaseConnection();
					$response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
					$body = $response->getBody();
					$body->write(xml_encode(message('OK', "Le cours a été ajouté avec succès."), "response"));
                }
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );
	
	
    /**
     * route - READ - get cours by id - GET method
     */
    $app->get
    (
        '/api/cours/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_cours where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $cours = $statement->fetch(PDO::FETCH_OBJ);
                    $cours_array = array( 
                        "id" => $cours->id, 
                        "idModule" => $cours->IdModule,
                        "idClasse" => $cours->idClasse,
                        "description" => $cours->description,             
                        "dateHeure" => $cours->dateHeure,             
                        "duree" => $cours->duree);           
                    $body->write(xml_encode($cours_array, "cours"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The course with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get cours by eleve signed in- GET method
     */
    $app->get
    (
        '/api/cours/eleve/{idEleve}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('idEleve');                
                            

                $sql = "select * from T_Cours where IdClasse in (select IdClasse from t_eleves where id=:id)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                // $statement->execute(array(':login' => $login,':password' => $password));
                if ($statement->rowCount()) {
                    $coursList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $cours_str = xml_encode_array ($coursList, "cours");
                    $body->write(xml_encode(array("coursList" => $cours_str)));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les cours suivis par l'étudiant avec id = '".$id."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get cours by enseignant signed in- GET method
     */
    $app->get
    (
        '/api/cours/enseignant/{idEnseignant}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('idEnseignant');                
                            
                $sql = "select * from T_Cours where Id in (select IdCours from t_enseignants_cours where IdEnseignant=:id)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
               
                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $coursList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $cours_str = xml_encode_array ($coursList, "cours");
                    $body->write(xml_encode(array("coursList" => $cours_str)));
                }
                else
                {
					$body->write(xml_encode(message('KO', "Les cours donnés par l'enseignant avec id = '".$id."'  n'ont pas été trouvés ou a été supprimés."), "response"));                                  
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            } 
            return $response;
        }
    );

    /**
     * route - READ - get all courses - GET method
     */
    $app->get
    (
        '/api/cours', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_cours";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $cours = $statement->fetchAll(PDO::FETCH_OBJ);
                    $cours_str = xml_encode_array ($cours, "cours");
                    $body->write(xml_encode(array("cours" => $cours_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucun cours n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a course by id - PUT method
     */
    $app->put
    (
        '/api/cours/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                $idModule = $params['idModule'];
                $idClasse = $params['idClasse'];
                $description = $params['description'];
                $dateHeure = $params['dateHeure'];
                $duree = $params['duree'];

                $sql = "update t_cours set IdModule = :idModule, idClasse = :idClasse, description = :description,dateHeure= :dateHeure,duree = :duree where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':idModule', $idModule);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':description', $description);
                $statement->bindParam(':dateHeure', $dateHeure);
                $statement->bindParam(':duree', $duree);
                $statement->execute();
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le cours a été mis à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO',  $exception->getMessage())));
            }
            return $response;
        }
    );
 
    /**
     * route - DELETE - delete a course by id - DELETE method
     */

    $app->delete
    (
        '/api/cours/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');
                $sql = "delete from t_cours where id = :id";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                $body->write(xml_encode(message('OK', "The course has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );

	
	
	 /** 
     * Devoir
     * 
     */

    $app->post
    (
        '/api/devoir/{idEns}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $idModule = $params['idModule'];
                $idClasse = $params['idClasse'];
                $statut = $params['statut'];
                $description = $params['description'];
                $dateHeure = $params['dateHeure'];
                $duree = $params['duree'];
                //$coef = $params['coef'];

				$idEns = $request->getAttribute('idEns');
				$idDevoir = "";
                

				
                $sql = "insert into T_devoirs (IdModule,idClasse,statut,description,dateHeure,duree,coef) values (:idModule,:idClasse,:statut,:description,:dateHeure,:duree,:coef)";

				$sql0= "Select coefficient From t_Modules Where id = :idModule";
				
				$sql1 = "Select id From t_Devoirs Where idModule = :idModule and idClasse = :idClasse and statut = :statut and coef =:coef and description = :description and dateHeure = :dateHeure and duree = :duree";
				
				$sql2 = "Insert Into T_Enseignants_Devoirs (idEnseignant,idDevoirs) values (:idEnseignant,:idDevoir)";

				
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

				
			    $statement0 = $db_connection->prepare($sql0);
				$statement0->bindParam(':idModule', $idModule);
				$statement0->execute();
				if ($statement0->rowCount()) {
					$coef=$statement0->fetch(PDO::FETCH_OBJ);
				}
				
				
                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':idModule', $idModule);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':description', $description);
                $statement->bindParam(':dateHeure', $dateHeure);
                $statement->bindParam(':duree', $duree);
                $statement->bindParam(':statut', $statut);
                $statement->bindParam(':coef', $coef->coefficient);
               
                $statement->execute();


				
			    $statement1 = $db_connection->prepare($sql1);
                $statement1->execute(array( 
                        "idModule" => $idModule,
                        "idClasse" => $idClasse,
						"statut" => $statut,
						"coef" => $coef->coefficient,
                        "description" => $description,             
                        "dateHeure" => $dateHeure,             
                        "duree" => $duree));
						
                if ($statement1->rowCount()) {
                    $idDevoir= $statement1->fetch(PDO::FETCH_OBJ);
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':idEnseignant', $idEns);
					$statement2->bindParam(':idDevoir', $idDevoir->id);
					$statement2->execute();
					
					$db_access->releaseConnection();
					$response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
					$body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le devoir a été enregistré avec succès."), "response"));
                }
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }
            return $response;
        }
    );

    /**
     * route - READ - get devoir by id - GET method
     */
    $app->get
    (
        '/api/devoir/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_devoirs where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $devoir = $statement->fetch(PDO::FETCH_OBJ);
                    $devoirs_array = array( 
                        "id" => $devoir->id, 
                        "idModule" => $devoir->IdModule,
                        "idClasse" => $devoir->idClasse,
                        "description" => $devoir->description,             
                        "dateHeure" => $devoir->dateHeure,             
                        "statut" => $devoir->statut,             
                        "coef" => $devoir->coef,             
                        "dateHeure" => $devoir->dateHeure,             
                        "duree" => $devoir->duree);           
                    $body->write(xml_encode($devoirs_array, "devoir"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The devoir with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get devoir for a eleve- GET method
     */
    $app->get
    (
        '/api/devoir/eleve/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                
                            

                $sql = "select * from T_devoirs where idClasse in (select idClasse from t_eleves where id=:id)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $devoirsList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $devoir_str = xml_encode_array ($devoirsList, "devoirs");
                    $body->write(xml_encode(array("devoirsList" => $devoir_str)));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les devoirs donnés par l'enseignant dont id = '".$id."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get devoir for a eleve- GET method
     */
    $app->get
    (
        '/api/devoir/enseignant/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                
                            
                
                $sql = "select * from T_devoirs where Id in (select IdDevoirs from t_enseignants_devoirs where idEnseignant=:id)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                // $statement->execute(array(':login' => $login,':password' => $password));
                if ($statement->rowCount()) {
                    $devoirsList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $devoir_str = xml_encode_array ($devoirsList, "devoirs");
                    $body->write(xml_encode(array("devoirsList" => $devoir_str)));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Les devoirs donnés par l'enseignant dont id = '".$id."'  n'ont pas été trouvés ou a été supprimés."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );



    /**
     * route - READ - get all devoir - GET method
    */
    $app->get
    (
        '/api/devoirs', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_devoirs";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $devoirs = $statement->fetchAll(PDO::FETCH_OBJ);
                    $devoir_str = xml_encode_array ($devoirs, "devoir");
                    $body->write(xml_encode(array("devoirs" => $devoir_str)));
                } else {
                    $body->write(xml_encode(message('KO', "Aucun devoir n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a devoir by id - PUT method
     */
    $app->put
    (
        '/api/devoir/{id}', 
        function (Request $request, Response $old_response) {
            try {

			
				$sql0= "Select coefficient From t_Modules Where id = :idModule";			

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

				$statement0 = $db_connection->prepare($sql0);						
			
                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                $idModule = $params['idModule'];
                $idClasse = $params['idClasse'];
                $statut = $params['statut'];
                $description = $params['description'];
                $dateHeure = $params['dateHeure'];
                $duree = $params['duree'];
				
                $sql = "update t_devoirs set IdModule = :idModule, idClasse = :idClasse, description = :description,dateHeure= :dateHeure,duree = :duree,coef = :coef,statut= :statut where id = :id";


				$statement0->bindParam(':idModule', $idModule);
				$statement0->execute();
				$coef=null;
				if ($statement0->rowCount()) {
					$coef=$statement0->fetch(PDO::FETCH_OBJ);
				}			

                
				$statement = $db_connection->prepare($sql);
				
                $statement->bindParam(':id', $id);
                $statement->bindParam(':idModule', $idModule);
                $statement->bindParam(':idClasse', $idClasse);
                $statement->bindParam(':description', $description);
                $statement->bindParam(':dateHeure', $dateHeure);
                $statement->bindParam(':duree', $duree);
                $statement->bindParam(':statut', $statut);
                $statement->bindParam(':coef', $coef->coefficient);
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le devoir a été mis à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xhtml+xml');
                $body = $response->getBody();
                //$body->write(xml_encode(message('KO',  $exception->getMessage()),"response"));
				 $body->write(xml_encode(message('OK', "Le devoir n' a été mis à jour avec succès.".$exception->getMessage()), "response"));
            }

            return $response;
        }
    );

    /**
     * route - DELETE - delete a devoir by id - DELETE method
     */
    $app->delete
    (
        '/api/devoir/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_devoirs where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The devoir has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );


	
	/** 
     * Classe- CREATE a diplome - POST Method
     * 
     */

    $app->post
    (
        '/api/diplome', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                $url = $params['url'];



                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

				
				$sql0 = "Select max(id) as id From T_Candidatures";
				
				$statement = $db_connection->prepare($sql0);
                $statement->execute();
				$idCandidature=0;
				while($ligneIdCandidature = $statement->fetch(PDO::FETCH_OBJ))
				{
					$idCandidature=$ligneIdCandidature->id;					
				}

                $sql = "insert into T_diplomes (idCandidature,url) values (:idCandidature,:url)";

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':idCandidature', $idCandidature);
                $statement->bindParam(':url', $url);
                
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le diplome a été enregistré avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * route - READ - get diploma by id - GET method
     */
    $app->get
    (
        '/api/diplome/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_diplomes where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $diplome = $statement->fetch(PDO::FETCH_OBJ);
                    $diplomes_array = array( 
                        "id" => $diplome->Id, 
                        "idCandidature" => $diplome->IdCandidature,           
                        "url" => $diplome->url);           
                    $body->write(xml_encode($diplomes_array, "diplome"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The diploma with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get dpiloma by url- GET method
     */
    $app->post
    (
        '/api/diplome/c', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                $url = $params['url'];
                $sql = "select * from t_diplomes where url = :url ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':url' => $url));
                if ($statement->rowCount()) {
                    $diplome = $statement->fetch(PDO::FETCH_OBJ);
                    $diplomes_array = array( 
                        "id" => $diplome->Id, 
                        "idCandidature" => $diplome->IdCandidature,
                        "url" => $diplome->url);
                       
                    $body->write(xml_encode($diplomes_array, "diplome"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Le diplome à l'adressse '".$login."'  n'a pas été trouvé ou a été supprimé."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );

    /**
     * route - READ - get all diplomas - GET method
     */
    $app->get
    (
        '/api/diplomes', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_diplomes";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $diplomeList = $statement->fetchAll(PDO::FETCH_OBJ);
                    $diplomes_str = xml_encode_array ($diplomeList, "diplome");
                    $body->write(xml_encode(array("diplomeList" => $diplomes_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucun diplome n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a diploma by id - PUT method
     */
    $app->put
    (
        '/api/diplome/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                $idCandidature = $params['idCandidature'];
                $url = $params['url'];

                $sql = "update t_diplomes set IdCandidature = :idCandidature, url = :url where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':idCandidature', $idModule);
                $statement->bindParam(':url', $url);
               
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le diplome a été mis à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO',  $exception->getMessage())));
            }

            return $response;
        }
    );

    /**
     * route - DELETE - delete a diploma by id - DELETE method
     */
    $app->delete
    (
        '/api/diplome/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_diplomes where id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The diploma has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );

	
	
	/**
     * Candidature - CREATE - add new candidature - POST method
     */
    $app->post
    (
        '/api/candidature', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
               
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $birthday = $params['birthday'];
                $cv = $params['cv'];
                $state = $params['state'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];
				$commentaires = $params['commentaires'];
				$idEnseignant = $params['idEnseignant'];

                
                $sql = "insert into T_Candidatures (idEnseignant,nom,prenom,genre,cv,state,birthday,commentaires,email,telephone,path) values (:idEnseignant,:nom,:prenom,:genre,:cv,:state,:birthday,:commentaires,:email,:telephone,:path)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':cv', $cv);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':genre', $genre);
                $statement->bindParam(':birthday', $birthday);
                $statement->bindParam(':state', $state);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
                $statement->bindParam(':path', $path);
                $statement->bindParam(':idEnseignant', $idEnseignant);
                $statement->bindParam(':commentaires', $commentaires);

                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La candidature a été ajoutée avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * Candidature - Check - a candidature - POST method
     */
    $app->post
    (
        '/api/candidature/c', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
               
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $birthday = $params['birthday'];
                $cv = $params['cv'];
                $state = $params['state'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];

                
                $sql = "select * from T_Candidatures where nom = :nom and  prenom = :prenom and birthday=:birthday and genre = :genre and state= :state and cv = :cv and email=:email and telephone=:telephone and path=:path";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':cv', $cv);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':genre', $genre);
                $statement->bindParam(':birthday', $birthday);
                $statement->bindParam(':state', $state);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
                $statement->bindParam(':path', $path);
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La candidature existe déja"), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

    /**
     * route - READ - get candidature by id - GET method
     */
    $app->get
    (
        '/api/candidature/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_Candidatures where id=:id";	
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();						
				$candidatureList=array();
				$statement = $db_connection->prepare($sql);
			    $statement->bindParam(':id', $id);
				$statement->execute();	
                while ( $candidature = $statement->fetch(PDO::FETCH_OBJ) ) {		 
					$idCandidature=$candidature->id;
					$sql0 = "Select * From T_Diplomes where idCandidature=:idCandidature";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':idCandidature', $idCandidature);
					$statement0->execute();					
                    $diplomeList = $statement0->fetchAll(PDO::FETCH_OBJ);
					$diplomeList_str = xml_encode_array ($diplomeList, "diplome");
                    array_push ($candidatureList, array( 
                        "id" => $candidature->id, 
                        "idEnseignant" => $candidature->idEnseignant,
                        "nom" => $candidature->nom,
                        "prenom" => $candidature->prenom,
                        "email" => $candidature->email,
                        "telephone" => $candidature->telephone,
                        "genre" => $candidature->genre,
                        "path" => $candidature->path,
                        "birthday" => $candidature->birthday,
						"state" => $candidature->state,
						"commentaires" => $candidature->commentaires,
						"diplomeList" => $diplomeList_str,
                        "cv" => $candidature->cv));					
				}
				if($candidatureList!=null)
				{				
					$candidature_str = xml_encode_array ($candidatureList, "");
					$body->write(xml_encode(array("candidature" => $candidature_str)));
                } 
				else {
                    $body->write(json_encode(message('KO', "Aucune candidature n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - READ - get candidature by enseignant id - GET method
     */

    $app->get
    (
        '/api/candidature/enseignant/{idEns}', 
        function (Request $request, Response $old_response, array $args) {
            try {
				
            $idEns = $request->getAttribute('idEns');                

			$sql = "Select * From T_Candidatures where idEnseignant=:idEns";
			
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$candidatureList=array();			
				
				$statement = $db_connection->prepare($sql);
				
				$statement->execute(array(':idEns' => $idEns));
				
                while ( $candidature = $statement->fetch(PDO::FETCH_OBJ) ) {				 
					$idCandidature=$candidature->id;
					$sql0 = "Select * From T_Diplomes where idCandidature=:idCandidature";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':idCandidature', $idCandidature);
					$statement0->execute();					
                    $diplome = $statement0->fetchAll(PDO::FETCH_OBJ);
					$diplomeList_str = xml_encode_array ($diplome,"diplome");
                    array_push ($candidatureList, array( 
                        "id" => $candidature->id, 
                        "idEnseignant" => $candidature->idEnseignant,
                        "nom" => $candidature->nom,
                        "prenom" => $candidature->prenom,
                        "email" => $candidature->email,
                        "telephone" => $candidature->telephone,
                        "genre" => $candidature->genre,
                        "path" => $candidature->path,
                        "birthday" => $candidature->birthday,
						"state" => $candidature->state,
						"commentaires" => $candidature->commentaires,
						"diplomeList" => $diplomeList_str,
                        "cv" => $candidature->cv));					
				}
				if($candidatureList!=null)
				{				
					$candidature_str = xml_encode_array ($candidatureList, "candidature");
					$body->write(xml_encode(array("candidatureList" => $candidature_str)));
                } 
				else {
                    $body->write(json_encode(message('KO', "Aucune candidature pour cet enseignant.")));
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );
	
	

    /**
     * route - READ - get all candidatures - GET method
     */
    $app->get
    (
        '/api/candidatures', 
        function (Request $request, Response $old_response) {
            try {

			$sql = "Select * From T_Candidatures";
			
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$candidatureList=array();
                $statement = $db_connection->query($sql);
                while ( $candidature = $statement->fetch(PDO::FETCH_OBJ) ) {		 
					$idCandidature=$candidature->id;
					$sql0 = "Select * From T_Diplomes where idCandidature=:idCandidature";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':idCandidature', $idCandidature);
					$statement0->execute();					
                    $diplomeList = $statement0->fetchAll(PDO::FETCH_OBJ);
					$diplomeList_str = xml_encode_array ($diplomeList, "diplome");
                    array_push ($candidatureList, array( 
                        "id" => $candidature->id, 
                        "idEnseignant" => $candidature->idEnseignant,
                        "nom" => $candidature->nom,
                        "prenom" => $candidature->prenom,
                        "email" => $candidature->email,
                        "telephone" => $candidature->telephone,
                        "genre" => $candidature->genre,
                        "path" => $candidature->path,
                        "birthday" => $candidature->birthday,
						"state" => $candidature->state,
						"commentaires" => $candidature->commentaires,
						"diplomeList" => $diplomeList_str,
                        "cv" => $candidature->cv));					
				}
				if($candidatureList!=null)
				{				
					$candidature_str = xml_encode_array ($candidatureList, "candidature");
					$body->write(xml_encode(array("candidatureList" => $candidature_str)));
                } 
				else {
                    $body->write(json_encode(message('KO', "Aucune candidature n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a candidature by id - PUT method
     */
    $app->put
    (
        '/api/candidature/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();

                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $birthday = $params['birthday'];
                $cv = $params['cv'];
                $idEns = $params['idEns'];
                $commentaires = $params['commentaires'];
                $state = $params['state'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];

                $sql = "update T_Candidatures set nom = :nom, prenom = :prenom,birthday=:birthday,idEnseignant=:idEns,commentaires=:commentaires, genre = :genre,state= :state,cv = :cv,email=:email,telephone=:telephone,path=:path where id=:id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':idEns', $idEns);
                $statement->bindParam(':commentaires', $commentaires);
                $statement->bindParam(':genre', $genre);
                $statement->bindParam(':state', $state);
                $statement->bindParam(':birthday', $birthday);
                $statement->bindParam(':cv', $cv);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
                $statement->bindParam(':path', $path);
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "La candidature a été mise à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage())));
            }

            return $response;
        }
    );

    /**
     * route - DELETE - delete a candidature by id - DELETE method
     */
    $app->delete
    (
        '/api/candidature/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from t_Candidatures where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "The candidature has been deleted successfully."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base.")));
            }
            return $response;
        }
    );
	
	
	

    /**
     * route - CREATE - add new enseignant - POST method
     */
    $app->post
    (
        '/api/enseignant', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $telephone = $params['telephone'];
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];
				$i=0;
				$idClasse=[];
				while(isset($params['idClasse'.$i]))
				{
					array_push($idClasse,$params['idClasse'.$i]);
					$i++;
				}

                $sql = "insert into T_Enseignants (nom,prenom,genre,login,password,email,telephone,path) values (:nom,:prenom,:genre,:login,:password,:email,:telephone,:path)";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
           
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':genre', $genre);             
                $statement->bindParam(':login', $login);
                $statement->bindParam(':password', $password);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
				$statement->bindParam(':path', $path);
                $statement->execute();


				$sql1 = "Select max(id) as id From T_Enseignants";
				
				$statement = $db_connection->prepare($sql1);
                $statement->execute();
				$idEnseignant=0;
				while($ligneIdClasse = $statement->fetch(PDO::FETCH_OBJ))
				{
					$idEnseignant=$ligneIdClasse->id;					
				}
					
				foreach( $idClasse as $value )
				{
					$sql2="Insert Into T_Enseignants_Classes_modules (IdEns, IdClasse) values (:idEnseignant,:idClasse)";
					$statement = $db_connection->prepare($sql2);
					$statement->bindParam(':idEnseignant', $idEnseignant);
					$statement->bindParam(':idClasse', $value);
					$statement->execute();
				}
				
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "L'enseignant a été ajouté avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }
            return $response;
        }
    );

    /**
     * route - READ - get enseignant by id - GET method
     */
    $app->get
    (
        '/api/enseignant/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {


				$sql = "Select * From T_Enseignants where id=:id";
				$id= $request->getAttribute('id'); 
			
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
				$enseignantLigne=array()
;               if ($statement->rowCount()) {
                    $enseignant = $statement->fetch(PDO::FETCH_OBJ); 
					$idEnseignant=$enseignant->Id;
					$sql0 = "Select IdClasse from T_Enseignants_Classes_modules Where IdEns=:IdEnseignant group by (IdClasse)";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':IdEnseignant', $idEnseignant);
					$statement0->execute();	
					$classe_str="";
					$idClasses=[];
					$listeClasse=array();
                    while($ligneIdClasse = $statement0->fetch(PDO::FETCH_OBJ))
					{
						$idClasse=$ligneIdClasse->IdClasse;
						array_push($idClasses,$ligneIdClasse);
						$sql1="Select * from T_Classes Where Id=:idClasse";
						$statement1 = $db_connection->prepare($sql1);
						$statement1->bindParam(':idClasse',$idClasse);
						$statement1->execute();	
						$classe = $statement1->fetch(PDO::FETCH_OBJ);
						array_push($listeClasse,$classe);	
						
						
					}
					$classe_str = xml_encode_array ($listeClasse, "classe");	
					
					$sql2 = "Select * from T_Candidatures Where IdEnseignant=:IdEnseignant";
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':IdEnseignant', $idEnseignant);
					$statement2->execute();	
					
                    $candidatureResponse = $statement2->fetchAll(PDO::FETCH_OBJ);
					$candidature_str = xml_encode_array ($candidatureResponse, "candidature");
					$idClasses_str = xml_encode_array ($idClasses, "");
                    $enseignantLigne = array( 
                        "id" => $enseignant->Id,
                        "nom" => $enseignant->nom,
                        "prenom" => $enseignant->prenom,
                        "email" => $enseignant->email,
                        "telephone" => $enseignant->telephone,
                        "genre" => $enseignant->genre,
                        "path" => $enseignant->path,
                        "login" => $enseignant->login,
						"password" => $enseignant->password,
						"idClasseList" =>$idClasses_str,
						"classeList" => $classe_str,
                        "candidatureList" => $candidature_str);					
				}
				if($enseignantLigne!=null)
				{				
					$enseignant_str  = xml_encode($enseignantLigne, "enseignant");
					$enseignantStr = array("enseignantList" => $enseignant_str);
					$body->write(xml_encode($enseignantStr,""));
                } 
				else {
                    $body->write(json_encode(message('KO', "Aucune candidature n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );


    /**
     * route - READ - get enseignant by login and password - GET method
     */
    $app->get
    (
        '/api/enseignant/{login}/{password}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $login = $request->getAttribute('login');                
                $password = $request->getAttribute('password');                

                $sql = "select * from T_Enseignants where login = :login and password = :password ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$statement = $db_connection->prepare($sql);
                $statement->execute(array(':login' => $login,
											':password' => $password));
											
				$enseignantLigne=array();
				if ($statement->rowCount()) {
                    $enseignant = $statement->fetch(PDO::FETCH_OBJ); 
					$idEnseignant=$enseignant->Id;
					$sql0 = "Select IdClasse from T_Enseignants_Classes_modules Where IdEns=:IdEnseignant group by(IdClasse)";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':IdEnseignant', $idEnseignant);
					$statement0->execute();	
					$classe_str="";
					$idClasses=[];
										
                    while($ligneIdClasse = $statement0->fetch(PDO::FETCH_OBJ))
					{
						$idClasse=$ligneIdClasse->IdClasse;
						array_push($idClasses,$ligneIdClasse);
						$sql1="Select * from T_Classes Where Id=:idClasse";
						$statement1 = $db_connection->prepare($sql1);
						$statement1->bindParam(':idClasse',$idClasse);
						$statement1->execute();	
						$classe = $statement1->fetchAll(PDO::FETCH_OBJ);
						$classe_str = xml_encode_array ($classe, "classe");	
					}
					
					$sql2 = "Select * from T_Candidatures Where IdEnseignant=:IdEnseignant";
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':IdEnseignant', $idEnseignant);
					$statement2->execute();	
					
                    $candidatureResponse = $statement2->fetchAll(PDO::FETCH_OBJ);
					$candidature_str = xml_encode_array ($candidatureResponse, "candidature");
					$idClasses_str = xml_encode_array ($idClasses, "");
                    $enseignantLigne = array( 
                        "id" => $enseignant->Id,
                        "nom" => $enseignant->nom,
                        "prenom" => $enseignant->prenom,
                        "email" => $enseignant->email,
                        "telephone" => $enseignant->telephone,
                        "genre" => $enseignant->genre,
                        "path" => $enseignant->path,
                        "login" => $enseignant->login,
						"password" => $enseignant->password,
						"idClasseList" =>$idClasses_str,
						"classeList" => $classe_str,
                        "candidatureList" => $candidature_str);					
				}
				if($enseignantLigne!=null)
				{				
					$enseignant_str  = xml_encode($enseignantLigne, "enseignant");
					$enseignantStr = array("enseignantList" => $enseignant_str);
					$body->write(xml_encode($enseignantStr,""));
                } 
				else {
                    $body->write(json_encode(message('KO', "Aucune candidature n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );


    /**
     * route - READ - get enseignantby login and password - GET method
     */
    $app->post
    (
        '/api/enseignant/check', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
               
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $sql = "select * from T_Enseignants where nom = :nom and prenom = :prenom and email = :email and telephone = :telephone and genre=:genre and  login = :login and password = :password ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':nom' => $nom,':prenom' => $prenom,':genre' => $genre,':email' => $email,':telephone' => $telephone,':login' => $login,':password' => $password));
               
                if ($statement->rowCount()) {
                    $enseignant = $statement->fetch(PDO::FETCH_OBJ);
                    $enseignant_array = array( 
                        "id" => $enseignant->Id, 
                        "nom" => $enseignant->nom,
                        "prenom" => $enseignant->prenom,
                        "genre" => $enseignant->genre,
                        "telephone" => $enseignant->telephone,
                        "login" => $enseignant->login,
                        "password" => $enseignant->password,
                        "email" => $enseignant->email);
                    $body->write(xml_encode($enseignant_array, "enseignant"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "L'enseignant avec login = '".$login."' et password ='".$password."' n'a pas été trouvé ou a été supprimé."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get all enseignants - GET method
     */
    $app->get
    (
        '/api/enseignants', 
        function (Request $request, Response $old_response) {
            try {
			$sql = "Select * From T_Enseignants";
			
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$enseignantList=array();
                $statement = $db_connection->query($sql);
                while ( $enseignant = $statement->fetch(PDO::FETCH_OBJ) ) {		 
					$idEnseignant=$enseignant->Id;
					$sql0 = "Select IdClasse from T_Enseignants_Classes_modules Where IdEns=:IdEnseignant group by (IdClasse)";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':IdEnseignant', $idEnseignant);
					$statement0->execute();	
					$classe_str="";
					$idClasses=[];
					$i=0;					
                    while($ligneIdClasse = $statement0->fetch(PDO::FETCH_OBJ))
					{
						$idClasse=$ligneIdClasse->IdClasse;
						array_push($idClasses,$ligneIdClasse);
						//$idClasses[$i]=$idClasse;
						$sql1="Select * from T_Classes Where Id=:idClasse";
						$statement1 = $db_connection->prepare($sql1);
						$statement1->bindParam(':idClasse',$idClasse);
						$statement1->execute();	
						$classe = $statement1->fetchAll(PDO::FETCH_OBJ);
						$classe_str = xml_encode_array ($classe, "classe");	
						$i++;
					}
					
					$sql2 = "Select * from T_Candidatures Where IdEnseignant=:IdEnseignant";
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':IdEnseignant', $idEnseignant);
					$statement2->execute();	
					
                    $candidatureResponse = $statement2->fetchAll(PDO::FETCH_OBJ);
					$candidature_str = xml_encode_array ($candidatureResponse, "candidature");
					$idClasses_str = xml_encode_array ($idClasses, "");
                    array_push ($enseignantList, array( 
                        "id" => $enseignant->Id,
                        "nom" => $enseignant->nom,
                        "prenom" => $enseignant->prenom,
                        "email" => $enseignant->email,
                        "telephone" => $enseignant->telephone,
                        "genre" => $enseignant->genre,
                        "path" => $enseignant->path,
                        "login" => $enseignant->login,
						"password" => $enseignant->password,
						"idClasseList" =>$idClasses_str,
						"classeList" => $classe_str,
                        "candidatureList" => $candidature_str));					
				}
				if($enseignantList!=null)
				{				
					$enseignant_str  = xml_encode_array ($enseignantList, "enseignant");
					$body->write(xml_encode(array("enseignantList" => $enseignant_str)));
                } 
				else {
                    $body->write(xml_encode(message('KO', "Aucun enseignant n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a enseignant by id - PUT method
     */
    $app->put
    (
        '/api/enseignant/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

				$params = $request->getQueryParams();
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $genre = $params['genre'];
                $telephone = $params['telephone'];
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $path = $params['path'];
				$idClasse=[];
				$i=0;
				while(isset($params['idClasse'.$i]))
				{
					array_push($idClasse,$params['idClasse'.$i]);
					$i++;
				}

                $sql ="Update T_Enseignants Set nom=:nom, prenom=:prenom, email=:email, genre=:genre,telephone=:telephone,login=:login, password=:password, path=:path Where id=:id";
                
				$db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
				$statement->bindParam(':id', $id);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':genre', $genre);             
                $statement->bindParam(':login', $login);
                $statement->bindParam(':password', $password);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
				$statement->bindParam(':path', $path);
                $statement->execute();


				$sql1 = "Delete From T_Enseignants_Classes_modules Where IdEns=:idEnseignant";
				
				$statement = $db_connection->prepare($sql1);
				$statement->bindParam(':idEnseignant',$id);
                $statement->execute();
					
				foreach( $idClasse as $value )
				{
					$sql2="Insert Into T_Enseignants_Classes_modules (IdEns, IdClasse) values (:idEnseignant,:idClasse)";
					$statement = $db_connection->prepare($sql2);
					$statement->bindParam(':idEnseignant',$id);
					$statement->bindParam(':idClasse', $value);
					$statement->execute();
				}
				
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "mise à jour avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }
            return $response;
        }
    );

	
	
	/**
     * Gestionnaire 
     * route - CREATE - add new gestionnaire - POST method
     */
    $app->post
    (
        '/api/gestionnaire', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
                
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $adresse = $params['adresse'];
                $ddn = $params['date_naissance'];
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];

                $sql = "insert into T_Gestionnaires (nom,prenom,adresse,date_naissance,login,password,email,telephone) values (:nom,:prenom,:adresse,:ddn,:login,:password,:email,:telephone)";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
               
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':adresse', $adresse);
                $statement->bindParam(':ddn', $ddn);
               
                $statement->bindParam(':login', $login);
                $statement->bindParam(':password', $password);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
               
                $statement->execute();
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le gestionnaire  a été ajouté avec succès."), "response"));
            } catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );

	
	
    /**
     * route - READ - get gestionnaire by id - GET method
     */
    $app->get
    (
        '/api/gestionnaire/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');                

                $sql = "select * from T_Gestionnaires where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));
                if ($statement->rowCount()) {
                    $gestionnaire = $statement->fetch(PDO::FETCH_OBJ);
                    $gestionnaire_array = array( 
                        "id" => $gestionnaire->id, 
                        "nom" => $gestionnaire->nom,
                        "prenom" => $gestionnaire->prenom,
                        "adresse" => $gestionnaire->adresse,
                        "date_naissance" => $gestionnaire->date_naissance,
                        "telephone" => $gestionnaire->telephone,
                        "login" => $gestionnaire->login,
                        "password" => $gestionnaire->password,
                        "email" => $gestionnaire->email);
                    $body->write(xml_encode($gestionnaire_array, "gestionnaire"));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "The gestionnaire with id = '".$id."' has not been found or has been deleted."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Unable to connect to the data base."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get gestionnaire by login and password - GET method
     */
    $app->get
    (
        '/api/gestionnaire/{login}/{password}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $login = $request->getAttribute('login');                
                $password = $request->getAttribute('password');                

                $sql = "select * from T_Gestionnaires where login = :login and password = :password ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xhml+xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':login' => $login,':password' => $password));
                if ($statement->rowCount()) {
                    $gestionnaires = $statement->fetchAll(PDO::FETCH_OBJ);
                    $gestionnaire_str = xml_encode_array ($gestionnaires, "gestionnaire");
                    $body->write(xml_encode(array("gestionnaireList" => $gestionnaire_str)));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Le gestionnaire avec login = '".$login."' et password ='".$password."' n'a pas été trouvé ou a été supprimé."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get gestionnaire by login and password - GET method
     */
    $app->post
    (
        '/api/gestionnaire/check', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $params = $request->getQueryParams();
               
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $adresse = $params['adresse'];
                $ddn = $params['date_naissance'];
                
                $login = $params['login'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $sql = "select * from T_Gestionnaires where nom = :nom and prenom = :prenom and adresse=:adresse and date_naissance=:ddn and email = :email and telephone = :telephone  and  login = :login and password = :password ;";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':ddn' => $ddn,':adresse' => $adresse,':nom' => $nom,':prenom' => $prenom,':email' => $email,':telephone' => $telephone,':login' => $login,':password' => $password));
               
                if ($statement->rowCount()) {
                    $gestionnaires = $statement->fetchAll(PDO::FETCH_OBJ);
                    $gestionnaire_str = xml_encode_array ($gestionnaires, "gestionnaire");
                    $body->write(xml_encode(array("gestionnaires" => $gestionnaire_str)));
                }
                else
                {                    
                    $body->write(xml_encode(message('KO', "Le gestionnaire n'a pas été trouvé ou a été supprimé."), "response"));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {                
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));
            }
            
            return $response;
        }
    );


    /**
     * route - READ - get all gestionnaires - GET method
     */
    $app->get
    (
        '/api/gestionnaires', 
        function (Request $request, Response $old_response) {
            try {
                $sql = "Select * From T_Gestionnaires";
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();
    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();

                $statement = $db_connection->query($sql);
                if ($statement->rowCount()) {
                    $gestionnaires = $statement->fetchAll(PDO::FETCH_OBJ);
                    $gestionnaire_str = xml_encode_array ($gestionnaires, "gestionnaire");
                    $body->write(xml_encode(array("gestionnaireList" => $gestionnaire_str)));
                } else {
                    $body->write(json_encode(message('KO', "Aucun gestionnaire n'a été enregistré pour le moment.")));
                }

                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données."), "response"));
            }
    
            return $response;
        }
    );

    /**
     * route - UPDATE - update a gestionnaire by id - PUT method
     */
    $app->put
    (
        '/api/gestionnaire/{id}', 
        function (Request $request, Response $old_response) {
            try {

                $id = $request->getAttribute('id');

                $params = $request->getQueryParams();
                $nom = $params['nom'];
                $prenom = $params['prenom'];
                $adresse = $params['adresse'];
               
                $ddn = $params['date_naissance'];
                $password = $params['password'];
                $email = $params['email'];
                $telephone = $params['telephone'];
                $login = $params['login'];
                $password = $params['password'];
                
                $sql = "update T_Gestionnaires set nom = :nom, prenom = :prenom, adresse = :adresse,date_naissance=:ddn,login = :login,password = :password
                ,email=:email,telephone=:telephone where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':id', $id);
                $statement->bindParam(':nom', $nom);
                $statement->bindParam(':prenom', $prenom);
                $statement->bindParam(':adresse', $adresse);
                $statement->bindParam(':ddn', $ddn);
                $statement->bindParam(':login', $login);
                $statement->bindParam(':password', $password);
                $statement->bindParam(':email', $email);
                $statement->bindParam(':telephone', $telephone);
               
                $statement->execute();

                $db_access->releaseConnection();

                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Le gestionnaire a été mis à jour avec succès."), "response"));
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-Type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.")));
            }

            return $response;
        }
    );

    /**
     * route - DELETE - delete a gestionnaire by id - DELETE method
     */
    $app->delete
    (
        '/api/gestionnaire/{id}', 
        function (Request $request, Response $old_response, array $args) {
            try {
                $id = $request->getAttribute('id');

                $sql = "delete from T_Gestionnaires where Id = :id";

                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();

                $statement = $db_connection->prepare($sql);
                $statement->execute(array(':id' => $id));

                $body->write(xml_encode(message('OK', "le gestionnaire a été supprimé avec succès."), "response"));
                $db_access->releaseConnection();
            } catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage())));
            }
            return $response;
        }
    );
	
    /**
     * route - CREATE- get bulletin - POST method
     */
    $app->post
    (
        '/api/bulletin/{semestre}', 
        function (Request $request, Response $old_response, array $args) {
            try {				
				$semestre= $request->getAttribute('semestre');

				//inserstion et creation des nouveaux bulletins vide				
				$sql= "INSERT INTO t_bulletins (idEleve, nom,prenom, genre, date, semestre) select id,nom,prenom,genre,DATE_FORMAT(SYSDATE(), '%Y-%m-%d') as date,:semestre  FROM t_eleves;";
				
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();

                $statement = $db_connection->prepare($sql);
                $statement->bindParam(':semestre', $semestre); 
                $statement->execute();
				
				//recuperation des nouveaux bulletins et des id des eleves
				$sql1="select id, idEleve from t_bulletins where semestre=:semestre;";
                $statement = $db_connection->prepare($sql1);
                $statement->bindParam(':semestre', $semestre); 
                $statement->execute();
				
				$RowIdBulletin_IdEleve=$statement->fetchAll(PDO::FETCH_OBJ);
				
				$moyenne=0;
				$effectif=0;
				$decision="Semestre invalidé";
				
				//insertion et calcul des notes dans t_bulletins_notes pour chaque bulletin
				foreach($RowIdBulletin_IdEleve as $row)
				{
					//inserstion des notes
					$sql2= "INSERT INTO t_bulletins_notes (idEleve,idBulletin, note, coef, notePondere, libelle) select :idEleve , :id , avg(n.note) as note, n.coef, avg(n.note)*n.coef as notePondere, m.libelle from t_note n, t_devoirs d,t_modules m where n.idDevoirs=d.id and d.idModule=m.id and n.idEleve=:idEleve  group by(d.idModule);";
					$statement = $db_connection->prepare($sql2);
					$statement->bindParam(':id', $row->id); 
					$statement->bindParam(':idEleve', $row->idEleve); 
					$statement->execute();
					
					//calcul de la moyenneGenerale
					$sql3="select sum(notePondere)/sum(coef) as MoyenneGenerale from t_bulletins_notes where idEleve=:idEleve and idBulletin=:idBulletin;";
					$statement = $db_connection->prepare($sql3);
					$statement->bindParam(':idEleve', $row->idEleve); 
					$statement->bindParam(':idBulletin', $row->id); 
					$statement->execute();
					$reponse=$statement->fetch(PDO::FETCH_OBJ);
					$moyenne = $reponse->MoyenneGenerale;
					
					if($moyenne >0 )
					{
						$decision = "Semestre Validé";
					}
					//calcul de l'effectif de la classe de l'eleve
					$sql4="select count(id) as effectif from t_eleves where idClasse in (select idClasse from t_eleves where id=:idEleve);";
					$statement = $db_connection->prepare($sql4);
					$statement->bindParam(':idEleve', $row->idEleve);  
					$statement->execute();
					$reponse=$statement->fetch(PDO::FETCH_OBJ);
					$effectif = $reponse->effectif;
					
					//mise à jour du bulletin avec la moyenneGenerale, decision...
					$sql5="update t_bulletins set decision=:decision, moyenneGenerale=:MoyenneGenerale, effectif=:effectif where id=:idBulletin and idEleve=:idEleve;";
					$statement = $db_connection->prepare($sql5);
					$statement->bindParam(':decision', $decision);
					$statement->bindParam(':MoyenneGenerale', $moyenne);
					$statement->bindParam(':effectif', $effectif);
					$statement->bindParam(':idBulletin', $row->id);
					$statement->bindParam(':idEleve', $row->idEleve);
					$statement->execute();		
				}
				
				//recuperation de la liste des libelle et id des classes
				$sql6="select id,libelle from t_classes;";
				$statement = $db_connection->prepare($sql6);
				$statement->execute();

				$rowClasses=$statement->fetchAll(PDO::FETCH_OBJ);
				
				//calcul du rang
				//parcours des classes
				foreach($rowClasses as $rowclass)
				{
					//recuperationde l'id de l'Eleve
					//affichage des id des eleve d'une classe par ordre de merite
					$sql7="select idEleve from t_bulletins where semestre=:semestre and idEleve in(select id from t_eleves where idClasse=:idClasse) order by (moyenneGenerale) desc;";
					$statement = $db_connection->prepare($sql7);
					$statement->bindParam(':semestre', $semestre);
					$statement->bindParam(':idClasse', $rowclass->id);
					$statement->execute();

					$listeEleve=$statement->fetchAll(PDO::FETCH_OBJ);
					
					//mise à jour du rang et du libelle classe 
					$j=1;
					foreach($listeEleve as $rowEleve)
					{
						$sql8="update t_bulletins set rang=:rang, classe=:classe where idEleve=:idEleve";
						$statement = $db_connection->prepare($sql8);
						$statement->bindParam(':rang',$j);
						$statement->bindParam(':classe', $rowclass->libelle);
						$statement->bindParam(':idEleve', $rowEleve->idEleve);
						$statement->execute();
						$j++;
					}
				}
                
                $db_access->releaseConnection();
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('OK', "Les bulletins ont ete cree avec succès."), "response"));
			} 
			
			catch (Exception $exception) {         
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', $exception->getMessage()), "response"));            
            }

            return $response;
        }
    );
	
	
	/**
     * route - READ - get all bulletins- GET method
     */
    $app->get
    (
        '/api/bulletins', 
        function (Request $request, Response $old_response) {
            try {
				
				$sql = "Select * From T_Bulletins";
			
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$bulletinList=array();
				
                $statement = $db_connection->query($sql);
                while ( $bulletin = $statement->fetch(PDO::FETCH_OBJ) ) {		 
					$idBulletin=$bulletin->id;
					$sql0 = " Select libelle, note,coef,notePondere From T_Bulletins_notes where idBulletin=:idBulletin ;";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':idBulletin', $idBulletin);
					$statement0->execute();							
                    $row=$statement0->fetchAll(PDO::FETCH_OBJ);
					$total="Total";
					$sql2 = "Select :total as libelle,sum(coef) as coef,sum(notePondere)/sum(coef) as note,sum(notePondere) as notePondere From T_Bulletins_notes where idBulletin=:idBulletin";
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':idBulletin', $idBulletin);
					$statement2->bindParam(':total',$total);
					$statement2->execute();	
					
					
                    $noteTotale = $statement2->fetch(PDO::FETCH_OBJ);
					
					array_push($row,$noteTotale);
					
					$row_str = xml_encode_array ($row, "row");
                    array_push ($bulletinList, array( 
                        "id" => $bulletin->id,
                        "nom" => $bulletin->nom,
                        "prenom" => $bulletin->prenom,
                        "idEleve" => $bulletin->idEleve,
                        "genre" => $bulletin->genre,
                        "decision" => $bulletin->decision,
                        "moyenneGenerale" => $bulletin->moyenneGenerale,
						"effectif" => $bulletin->effectif,
						"rang" => $bulletin->rang,
						"classe" => $bulletin->classe,
						"semestre" => $bulletin->semestre,
						"rowList" =>$row_str));	
					
				}
				
				if($bulletinList!=null)
				{				
					$bulletin_str  = xml_encode_array ($bulletinList, "bulletin");
					$body->write(xml_encode(array("bulletinList" => $bulletin_str)));
                } 
				else {
                    $body->write(xml_encode(message('KO', "Aucune bulletin n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();			
			
			
            } 
			
			catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );	



	
	/**
     * route - READ - get all bulletins by eleve -GET method
     */
    $app->get
    (
        '/api/bulletin/{idEleve}', 
        function (Request $request, Response $old_response) {
            try {
				
				$idEleve = $request->getAttribute('idEleve');                

				
				$sql = "Select * From T_Bulletins where idEleve=:idEleve";
			
                $db_access = new DBAccess ();
                $db_connection = $db_access->getConnection();    
                $response = $old_response->withHeader('Content-type', 'application/xhtml+xml');
                $body = $response->getBody();			
				
				$bulletinList=array();
				
				$statement = $db_connection->prepare($sql);
				$statement->bindParam(':idEleve', $idEleve);
				$statement->execute();	
              
                while ( $bulletin = $statement->fetch(PDO::FETCH_OBJ) ) {		 
					$idBulletin=$bulletin->id;
					$sql0 = " Select libelle, note,coef,notePondere From T_Bulletins_notes where idBulletin=:idBulletin ;";
					$statement0 = $db_connection->prepare($sql0);
					$statement0->bindParam(':idBulletin', $idBulletin);
					$statement0->execute();			
					$row=$statement0->fetchAll(PDO::FETCH_OBJ);
					$total="Total";
					$sql2 = "Select :total as libelle,sum(coef) as coef,sum(notePondere)/sum(coef) as note,sum(notePondere) as notePondere From T_Bulletins_notes where idBulletin=:idBulletin";
					$statement2 = $db_connection->prepare($sql2);
					$statement2->bindParam(':idBulletin', $idBulletin);
					$statement2->bindParam(':total',$total);
					$statement2->execute();	
					
					
                    $noteTotale = $statement2->fetch(PDO::FETCH_OBJ);
					
					array_push($row,$noteTotale);
					
					$row_str = xml_encode_array ($row, "row");
                    array_push ($bulletinList, array( 
                        "id" => $bulletin->id,
                        "nom" => $bulletin->nom,
                        "prenom" => $bulletin->prenom,
                        "idEleve" => $bulletin->idEleve,
                        "genre" => $bulletin->genre,
                        "decision" => $bulletin->decision,
                        "moyenneGenerale" => $bulletin->moyenneGenerale,
						"effectif" => $bulletin->effectif,
						"rang" => $bulletin->rang,
						"classe" => $bulletin->classe,
						"semestre" => $bulletin->semestre,
						"rowList" =>$row_str));	
					
				}
				
				if($bulletinList!=null)
				{				
					$bulletin_str  = xml_encode_array ($bulletinList, "bulletin");
					$body->write(xml_encode(array("bulletinList" => $bulletin_str)));
                } 
				else {
                    $body->write(xml_encode(message('KO', "Aucune bulletin n'a été enregistrée pour le moment.")));
                }
                $db_access->releaseConnection();			
			
			
            } 
			
			catch (Exception $exception) {
                $response = $old_response->withHeader('Content-type', 'application/xml');
                $body = $response->getBody();
                $body->write(xml_encode(message('KO', "Impossible de se connecter à la base de données.".$exception->getMessage()), "response"));
            }
    
            return $response;
        }
    );	
	


	
	$app->run();

	
?>