interface Role {
    id: number;
    name: string;
    authority: string;
}

interface User {
    id: number;
    username: string;
    password: string;
    name: string;
    lastName: string;
    age: number;
    email: string;
    roles: string[];
    stringRoles: string;
}

const ADMIN: string = "ADMIN";
const USER: string = "USER";
const CREATE_USER: string = "create-user";

let usersTableContainer: HTMLElement | null;
let adminSideButton: HTMLElement | null;
let userSideButton: HTMLElement | null;
let mainContent: HTMLElement | null;


window.onload = printPage;

let pageState: string = ADMIN;

async function printPage() {
    usersTableContainer = document.getElementById("users-table-container");
    mainContent = document.getElementById("content");
    const user: User= await fetchUser();
    const isAdmin : boolean = user.stringRoles.includes(ADMIN);
    // @ts-ignore
    document.getElementById('username').innerText = user.name;
    // @ts-ignore
    document.getElementById('usersRoles').innerText = user.stringRoles;

    adminSideButton = document.getElementById('adminSideButton');
    userSideButton = document.getElementById('userSideButton');

    // pageState = isAdmin ? ADMIN : USER;

    switch (pageState) {
        case ADMIN:
            displayUsers();
            break;
        case USER:
            displayUser();
            break;
        case CREATE_USER:
            displayCreateUserForm;
            break;
    }

}

function clearUsersTableContainer() {
    // @ts-ignore
    // usersTableContainer.innerHTML = `
    //     <div class="mt-3" id="users-table-container">
    //         <h5>All users</h5>
    //         <table class="table table-bordered" id="users-table">
    //             <thead class="thead-light">
    //             <tr>
    //                 <th>ID</th>
    //                 <th>First Name</th>
    //                 <th>Last Name</th>
    //                 <th>Age</th>
    //                 <th>Email</th>
    //                 <th>Role</th>
    //                 <th>Edit</th>
    //                 <th>Delete</th>
    //             </tr>
    //             </thead>
    //             <tbody>
    //             </tbody>
    //         </table>
    //     </div>`;
}

function adminHandler() {
    console.log("Admin handler");
    pageState = ADMIN;
    printPage();
}

function userHandler() {
    console.log("User handler");
    pageState = USER;
    clearUsersTableContainer();
    printPage();
}

function createUserHandler() {
    console.log("Create user handler");
}

function displayUser() {
    // @ts-ignore
    adminSideButton.classList.remove("active");
    // @ts-ignore
    userSideButton.classList.add("active");
    console.log("Display user");
}

function displayCreateUserForm() {
    console.log("Create user");
}

async function fetchUsers(): Promise<User[]> {
    try {
        const response = await fetch('http://localhost:8080/api/admin/users', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // Add any additional headers (e.g., authorization token) if needed
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const users: User[] = await response.json();
        return users;
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function fetchRoles(): Promise<Role[]> {
    try {
        const response = await fetch('http://localhost:8080/api/admin/roles', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // Add any additional headers (e.g., authorization token) if needed
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const roles: Role[] = await response.json();
        return roles;
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function fetchUser(): Promise<User> {
    try {
        const response = await fetch(`http://localhost:8080/api/user`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        // const user : User = await response.json();
        // console.log("Fetched user: ", user);

        return response.json();
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function deleteUser(id: number): Promise<String> {
    try {
        // @ts-ignore
        event.preventDefault();
        const response = await fetch('http://localhost:8080/api/admin/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
                // Add any additional headers (e.g., authorization token) if needed
            },
            body: JSON.stringify(id)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        displayUsers();
        return "User was deleted";
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function updateUser(userId: number): Promise<String> {
    try {
        // @ts-ignore
        event.preventDefault();

        // @ts-ignore
        const form: HTMLFormElement = document.getElementById("editUserForm" + userId);

        const formData =  new FormData(form);

        const fetchedUser: User = await fetchUser();

        console.log("Fetched user: ", fetchedUser);


        const roles = Array
            .from(
                form
                    .querySelectorAll('input[name="roles"]:checked')
            )
            // @ts-ignore
            .map(checkbox => checkbox.value);
        console.log("Roles:", roles);
        const updatedUser = {
            id: formData.get('id'),
            username: formData.get('username'),
            password: formData.get('password'),
            name: formData.get('name'),
            lastName: formData.get('lastName'),
            age: formData.get('age'),
            email: formData.get('email'),
            roles: roles
        };

        console.log("Updated user: ", updatedUser);

        // formData.forEach((value, key) => {
        //     if (key in updatedUser) {
        //         // @ts-ignore
        //         updatedUser[key] = value;
        //     }
        // });

        const response = await fetch('http://localhost:8080/api/admin/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUser)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        displayUsers();
        return "User was updated";
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function displayUsers() {
    try {
        // @ts-ignore
        adminSideButton.classList.add("active");
        // @ts-ignore
        userSideButton.classList.remove("active");

        const users = await fetchUsers();
        console.log('Fetched users:', users);


        const roles = await fetchRoles();
        console.log('Fetched roles:', roles);

        const tableHead = document.createElement('div');
        tableHead.innerHTML = `
        <div class="mt-3" id="users-table-container">
        </div>`;



        // @ts-ignore
        mainContent.appendChild(tableHead);

        clearUsersTableContainer();
        // Get the container element
        const container = document.getElementById('users-table-container');

        if (container) {
            // Create a table element
            let table:HTMLElement | null = document.getElementById('users-table');

            // @ts-ignore
            table.innerHTML = ``;
            // Create table rows for each user
            users.forEach(user => {
                const row = document.createElement('tr');
                row.id = String(user.id);

                const cells = [
                    user.id.toString(),
                    user.name,
                    user.lastName,
                    user.age,
                    user.email,
                    user.stringRoles
                ];

                cells.forEach(cellText => {
                    const td = document.createElement('td');
                    // @ts-ignore
                    td.textContent = cellText;
                    row.appendChild(td);
                });

                // Edit button and modal
                const editTd = document.createElement('td');
                editTd.classList.add('table-buttons');
                const rolesBlock :HTMLElement = document.createElement('div');


                roles.forEach(role => {
                    const div = document.createElement('div');
                    div.innerHTML = `
                    <input type="checkbox" name="roles" value="${role.id}"/>
                    <label for="${role.id}">${role.name}</label><br/>
                    `;
                    rolesBlock.appendChild(div);
                });

                // <div th:each="role : ${roles}">
                // <input type="checkbox" name="roles" th:value="${role.id}"/>
                //     <label th:for="${role.id}" th:text="${role.name}"></label><br/>
                //     </div>

                editTd.innerHTML = `
        <button type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#editUserModal${user.id}">
            Edit
        </button>
        <div
                class="modal fade"
                id="editUserModal${user.id}"
                tabindex="-1"
                aria-labelledby="editUserModalLabel"
                aria-hidden="true"
        >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form
                            id="editUserForm${user.id}"
                        >
                            <input type="hidden" name="id" value="${user.id}">
                            <div class="form-group">
                                <label>Username</label>
                                <input 
                                    value="${user.username}" 
                                    type="text" 
                                    class="form-control" 
                                    name="username"
                                >
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input 
                                    value="${user.password}" 
                                    type="password" 
                                    class="form-control" 
                                    name="password"
                                >
                            </div>
                            <div class="form-group">
                                <label>First Name</label>
                                <input 
                                    value="${user.name}" 
                                    type="text" 
                                    class="form-control" 
                                    name="name"
                                >
                            </div>
                            <div class="form-group">
                                <label>Last Name</label>
                                <input value="${user.lastName}" type="text" class="form-control" name="lastName">
                            </div>
                            <div class="form-group">
                                <label>Age</label>
                                <input value="${user.age}" type="number" class="form-control" name="age">
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <input 
                                    value="${user.email}" 
                                    type="email" 
                                    class="form-control" 
                                    name="email"
                                    onchange=""
                                >
                            </div>
                            <label>Roles</label>
                            ${rolesBlock.innerHTML}
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button
                                        class="btn btn-primary"
                                        data-dismiss="modal"
                                        onclick="updateUser(${user.id})"
                                >
                                    Edit
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    `;
                row.appendChild(editTd);

                // Delete button and modal
                const deleteTd = document.createElement('td');
                deleteTd.classList.add('table-buttons');
                deleteTd.innerHTML = `
        <button 
            type="button" 
            class="btn btn-sm btn-danger" 
            data-toggle="modal" 
            data-target="#deleteUserModal${user.id}">
            Delete
        </button>
        <div
            class="modal fade"
            id="deleteUserModal${user.id}"
            tabindex="-1"
            aria-labelledby="editUserModalLabel"
            aria-hidden="true"
        >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteUserModalLabel">Delete User?</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>ID</label>
                                <input
                                        type="text"
                                        class="form-control"
                                        name="id"
                                        readonly
                                        value="${user.id}"
                                >
                            
                            </div>
                            <div class="form-group">
                                <label>First Name</label>
                                <input
                                        type="text"
                                        class="form-control"
                                        readonly
                                        value="${user.username}"
                                >
                            </div>
                            <div class="form-group">
                                <label>First Name</label>
                                <input
                                        type="text"
                                        class="form-control"
                                        readonly
                                        value="${user.name}"
                                >
                            </div>
                            <div class="form-group">
                                <label>Last Name</label>
                                <input
                                        type="text"
                                        class="form-control"
                                        readonly
                                        value="${user.lastName}"
                                >
                            </div>
                            <div class="form-group">
                                <label>Age</label>
                                <input
                                        type="number"
                                        class="form-control"
                                        readonly
                                        value="${user.age}"
                                >
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <input
                                        type="email"
                                        class="form-control"
                                        readonly
                                        value="${user.email}"
                                >
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                    Close
                                </button>
                                <button
                                        class="btn btn-primary"
                                        value="Delete"
                                        data-dismiss="modal"
                                        onclick="deleteUser(${user.id})"
                                >
                                    Delete
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    `;
                row.appendChild(deleteTd);

                // @ts-ignore
                table.appendChild(row);
            });
        } else {
            console.error('Container element not found');
        }
    } catch (error) {
        console.error('Failed to fetch users:', error);
    }
}
