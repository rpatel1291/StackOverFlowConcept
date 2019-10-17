//db.auth('', '')
db.createUser(
          {
              user: "root",
              pwd: "password",
              roles: [
                  {
                      role: "root",
                      db: "admin"
                  }
              ]
          }
  );
//  db = db.getSiblingDB("admin");
  db.auth('root','password')
  db.createUser(
          {
              user: "root",
              pwd: "password",
              roles: [
                  {
                      role: "root",
                      db: "stackoverflow"
                  }
              ]
          }
  );


db = db.getSiblingDB("stackoverflow");
//db.auth('root','password')
