# REST-and-Persistence

A set of REST APIs to manage entities and relationships in a mini gaming website.

->API hosted in Amazon EC2.
->Used JPA for persistence, and each API method must be transactional.

There are two primary types of entities: Players and Sponsors. They have the following relationships and constraints:
● Opponents: if two players play against each other, they are opponents. The opponent relationship is symmetric in that is Alice is an opponent of Bob, then Bob is also an opponent of Alice. A player can have zero or more opponent players.
● Sponsors: a player can optionally be sponsored by an external sponsor. Different players can have the same sponsor. The firstname, lastname, and email fields are required for any player. Emails must be unique across players.
The name field is required for any Sponsor, and does not need to be unique. For convenience and clarity, you are recommended to embed addresses and not to store them as separate entities.

package   edu.sjsu.cmpe275.lab2;
public   class   Player   {             

          private   long   id; 
          private   String   firstname;
          private   String   lastname;
          private   String   email;
          private   String   description;
          private   Address   address;
          private   Sponsor   sponsor;
          private   List<Player>   opponents;
          //   constructors,   setters,   getters,   etc.
            }

public   class   Address   {
            private   String   street;             
            private   String   city;
            private   String   state;
            private   String   zip;
            ...
}

public   class   Sponsor   {
            private   long   id;
            private   String   name;
            private   String   description;             
            private   Address   address;             
            ...
}

You need to persist these entities in a non-volatile database of your own choice. You may want
to   create   three   tables,   PLAYER,   SPONSOR,   and   OPPONENTS.
To manage these entities and their relationships, you are asked to provide the following REST
APIs. The paths below are relative to the base URL of your app. Player APIs

(1)   Create a player
Path:   player?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$... Method:   POST
This API creates a player object.
For simplicity, all the player fields (firstname, lastname, email, street, city, sponsor, etc), except ID and opponents,are passed in as query parameters. Only the firstname, lastname, and email are required. Anything else is optional. Opponents is not allowed to be passed in as a parameter.
The sponsor parameter, if present, must be the ID of an existing sponsor. The request returns the newly created player object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.) If the request is invalid, e.g. missing   required   parameters,   the   HTTP   status   code   should   be 400 otherwise 200.

(2)   Get   a   player
Path:player/{id}
Method:   GET
This   returns   a   full   player   object   in   JSON   in   its   HTTP   payload.

 All   existing   fields,   including   the   optional   sponsor   and   list   of   opponents   should   be   returned.
The   JSON   should   contain   the   full   sponsor   object,   if   present.
The list of opponents can be either (a) list of player IDs, or (b) list of “shallow” player objects that do not have their opponents list populated. If you take option (b), you want to use techniques like lazy loading to avoid serializing the whole game network starting from the requested player in   the   JSON   to   be   returned.
If the player of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.

(3)   Update   a   player
Path:   player/{id}?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$... Method:   POST
This API pdates a player object.
For simplicity, all the player fields (firstname, lastname, email, street, city, sponsor, etc), except opponents, should be passed in as query parameters. Required fields like email must be present. The object constructed from the parameters will completely replace the existing object in the server, except that it does not change the player’s ist of opponents.
Similar to the get method, the request returns the updated player object, including all attributes (first name, last name, email, opponents, sponsor, etc), in JSON. If the player ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.

(4)   Delete a player, URL: http://player/{id} Method: DELETE
This   deletes   the   player   object   with   the   given   ID.
If   the   player   with   the   given   ID   does   not   exist,   return   404.
Otherwise, delete the player and remove any reference of this player from your persistence of opponent   relations,   and   return   HTTP   status   code   200   and   the   deleted   player   in   JSON.
Sponsor   APIs

(5)   Create a sponsor
Path:   sponsor?name=XX&description=YY&street=ZZ&... Method:   POST
This   API   creates   an   sponsor   object.
For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query   parameters.   Only   name   is   required.

 The request returns the newly created sponsor object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.)
If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise   200.

(6)   Get a sponsor Path:sponsor/{id} Method:   GET
This   returns   a   full   sponsor   object   in   JSON   in   its   HTTP   payload.
All   existing   fields,   including   name,   description,   street,   and   city,   should   be   returned.
If the sponsor of the given ID does not exist, the HTTP return code should be 404; otherwise, 200.

(7)   Update   a   sponsor
Path:   sponsor/{id}?name=XX&description=YY&street=ZZ&... Method:   POST
This   API   updates   a   sponsor   object.
For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query   parameters.   Only   name   is   required.
Similar to the get method, the request returns the updated sponsor object, including all attributes in JSON. If the sponsor ID does not exist, 404 should be returned. If required parameters   are   missing,   return   400   instead.   Otherwise,   return   200.

(8)   Delete   a   sponsor URL:   http://sponsor/{id} Method:   DELETE
This   method   deletes   the   sponsor   object   with   the   given   ID.
If   there   is   still   any   player   belonging   to   this   sponsor,   return   400.
If   the   sponsor   with   the   given   ID   does   not   exist,   return   404.
Return   HTTP   code   200   and   the   deleted   object   in   JSON   if   the   object   is   deleted;
Opponent’s   APIs

(9)   Add   an   opponent Path:opponents/{id1}/{id2} Method:   PUT
This   makes   the   two   players   with   the   given   IDs   opponents   with   each   other.
If   either   player   does   not   exist,   return   404.
If   the   two   players   are   already   opponents,   do   nothing,   just   return   200.   Otherwise,
Record this opponent relation. If all is successful, return HTTP code 200 and any informative text message in the HTTP
payload.

(10)   Remove   an   opponent Path:opponents/{id1}/{id2} Method:   DELETE
This   request   removes   the   opponent   relation   between   the   two   players.
If   either   player   does   not   exist,   return   404.
If   the   two   players   are   not   opponents,   return   404.   Otherwise,
Remove this opponent relation. Return HTTP code 200 and a meaningful text message if all is successful.
Additional   Requirements/Constraints
