print('Start');

db = db.getSiblingDB('foreignWork');
db = db.getSiblingDB('userDetails');

db.createUser({
    user: 'work',
    pwd: 'work',
    roles: [{ role: 'readWrite', db: 'foreignWork' },
            { role: 'readWrite', db: 'userDetails' }],
    mechanisms: ["SCRAM-SHA-1"]
});

print('End');
