DOWNLOAD ALTAIR FOR CHROME TO RUN THESE QUERIES


http://localhost:7000/graphql     


USE POST METHODS FOR ALL ENDPOINTS




FIND ALL AVAILABLE COURIERS

{
  findAvailableCouriers(available: true) {
    courierId
    firstName
    lastName
    password
    email
    available
  }
}



CREATE A NEW COURIER

mutation {
  addCourier(
    firstName: "Julemand"
    lastName: "Hansen"
    password: "junglemand"
    email: "julemanden@julemanden.jul"
    available: true
  ) {
    courierId
    firstName
    lastName
    email
    password
    available
  }
}



ONLY UPDATES IF COURIER EXISTS ELSE CREATES NEW COURIER

mutation {
  update(
    id: 9
    firstName: "Rasmus"
    lastName: "Jull"
    password: "junglemand"
    email: "julemanden@julemanden.jul"
    available: true
  ) {
    courierId
    firstName
    lastName
    email
    password
    available
  }
}



LIST OF ALL COURIERS

{
  findAllCouriers {
    courierId
    firstName
    lastName
    email
    password
    available
  }
}



FIND A SPECEFIC COURIER BY ID

{
  findOneCourier(id: 9) {
    courierId
    firstName
    lastName
    password
    email
    available
  }
}



DELETE AND RETURN NULL IF NOT EXISTS THEN FAIL TO EXECUTE

mutation {
  delete(id: 9) {
    courierId
    firstName
    lastName
    email
    password
    available
  }
}


