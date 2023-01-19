instance = new Mongo().getDB("twitter");

instance.createCollection("users");

instance.users.createIndex({
  username: "text"
},{
  unique: true,
});

instance.createCollection("tweets");