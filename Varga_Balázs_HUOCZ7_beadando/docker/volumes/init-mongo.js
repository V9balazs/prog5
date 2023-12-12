print('Start');

db = db.getSiblingDB('foreignWork');

db.createUser({
    user: 'manager',
    pwd: 'manager',
    roles: [{ role: 'readWrite', db: 'foreignWork' }],
    mechanisms: ["SCRAM-SHA-1"]
});

db.createUser({
    user: 'employeeA',
    pwd: 'employeeA',
    roles: [{ role: 'read', db: 'foreignWork' }],
    mechanisms: ["SCRAM-SHA-1"]
});

db.createUser({
    user: 'employeeB',
    pwd: 'employeeB',
    roles: [{ role: 'read', db: 'foreignWork' }],
    mechanisms: ["SCRAM-SHA-1"]
});

db.createUser({
    user: 'employeeC',
    pwd: 'employeeC',
    roles: [{ role: 'read', db: 'foreignWork' }],
    mechanisms: ["SCRAM-SHA-1"]
});

print('End');
