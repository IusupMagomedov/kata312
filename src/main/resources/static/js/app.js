"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function fetchUsers() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const response = yield fetch('http://localhost:8080/admin/users', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers (e.g., authorization token) if needed
                },
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const users = yield response.json();
            return users;
        }
        catch (error) {
            console.error('Error fetching users:', error);
            throw error;
        }
    });
}
function displayUsers() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const users = yield fetchUsers();
            console.log('Fetched users:', users);
            // Display users in the UI or process them further
            users.forEach(user => {
                console.log(`User: ${user.name} ${user.lastName}, Email: ${user.email}, Roles: ${user.stringRoles}`);
            });
        }
        catch (error) {
            console.error('Failed to fetch users:', error);
        }
    });
}
// Call the function to fetch and display users
displayUsers();
