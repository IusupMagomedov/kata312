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
let adminContentToggle: HTMLElement | null;
let createContentToggle: HTMLElement | null;
let isAdmin: boolean | null;


window.onload = printPage;

let pageState: string = ADMIN;

async function printPage() {
    usersTableContainer = document.getElementById("users-table-container");
    mainContent = document.getElementById("content");
    adminContentToggle = document.getElementById("navbar-toggle-table");
    createContentToggle = document.getElementById("navbar-toggle-create");

    const user: User= await fetchUser();
    isAdmin = user.stringRoles.includes(ADMIN);
    // @ts-ignore
    document.getElementById('username').innerText = user.name;
    // @ts-ignore
    document.getElementById('usersRoles').innerText = user.stringRoles;



    adminSideButton = document.getElementById('adminSideButton');
    userSideButton = document.getElementById('userSideButton');

    pageState = !isAdmin ? USER : pageState;


    switch (pageState) {
        case ADMIN:
            printAdminsPage();
            break;
        case USER:
            printUsersPage(user);
            break;
        case CREATE_USER:
            printCreateUserPage();
            break;
    }

}



async function clearUsersTableContainer() {
    // @ts-ignore
    mainContent.innerHTML = `
        <h2>Admin Panel</h2>
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" id="navbar-toggle-table" href=# onclick="adminHandler()">Users table</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=# id="navbar-toggle-create" onclick="createUserToggleHandler()">New User</a>
            </li>
        </ul>`;

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

function createUserToggleHandler() {
    pageState = CREATE_USER;
    printPage();
    console.log("Create user handler");
}

function printUsersPage(user : User) {
    if(!isAdmin) {
        // @ts-ignore
        document
             .getElementById("adminSideButton")
             .classList
             .add("d-none");
    }
    // @ts-ignore
    adminSideButton.classList.remove("active");
    // @ts-ignore
    userSideButton.classList.add("active");
    // @ts-ignore
    mainContent.innerHTML=`
            <table class="table table-bordered">
                <thead class="thead-light">
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Role</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.stringRoles}</td>
                </tr>
                </tbody>
            </table>`
    console.log("Display user");
}

function printCreateUserPage() {
    console.log("Create user");

    clearUsersTableContainer();
    // @ts-ignore
    document.getElementById("navbar-toggle-table").classList.remove("active");
    // @ts-ignore
    document.getElementById("navbar-toggle-create").classList.add("active");


    // @ts-ignore
    mainContent.innerHTML = `
    <h2>Admin Panel</h2>
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link" id="navbar-toggle-table" href=# onclick="adminHandler()">Users table</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href=# id="navbar-toggle-create" onclick="createUserHandler()">New User</a>
            </li>
        </ul>
        <div class="mt-3">
            <h6>Add new user</h6>
            <div class="container-fluid p-4 d-flex justify-content-center">
                <div class="card card-container">
                    <div class="card-body">
                        <form class="justify-content-center" id="createUserForm">
                            <label class="form-label">Username</label>
                            <input 
                                type="text" 
                                class="form-control text-dark" 
                                name="username" 
                                value="yoshi">
                            <label class="form-label">Password</label>
                            <input type="password" class="form-control" value="yoshi" name="password" >
                            <label class="form-label">First name</label>
                            <input type="text" class="form-control text-dark" value="Yoshi" name="name" required>
                            <label class="form-label">Last name</label>
                            <input type="text" class="form-control text-dark" value="Dino" name="lastName" >
                            <label class="form-label">Age</label>
                            <input type="number" class="form-control" value="27" name="age" >
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control text-dark" value="yoshi@gmail.com" name="email" >
                            <div>
                                <div class="form-check">
                                    <input 
                                        class="form-check-input" 
                                        type="checkbox" 
                                        name="roles" 
                                        value="1">
                                    <label 
                                        class="form-check-label" 
                                    >ADMIN</label>
                                </div>
                                <div class="form-check">
                                    <input 
                                        class="form-check-input" 
                                        type="checkbox" 
                                        name="roles" 
                                        value="2"
                                        checked>
                                    <label 
                                        class="form-check-label" 
                                    >USER</label>
                                </div>
                            </div>
                            <button 
                                class="btn btn-success w-100"
                                onclick="createUser()"
                            >
                                Add new user
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>`;
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

        printPage();
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

        printPage();
        return "User was updated";
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function createUser(): Promise<String> {
    try {
        // @ts-ignore
        event.preventDefault();
        // @ts-ignore
        const form: HTMLFormElement = document.getElementById("createUserForm");
        console.log(form);
        const formData =  new FormData(form);

        // const fetchedUser: User = await fetchUser();
        //
        // console.log("Fetched user: ", fetchedUser);
        //
        //
        const roles = Array
            .from(
                form
                    .querySelectorAll('input[name="roles"]:checked')
            )
            // @ts-ignore
            .map(checkbox => checkbox.value);
        console.log("Roles:", roles);
        const createdUser = {
            id: formData.get('id'),
            username: formData.get('username'),
            password: formData.get('password'),
            name: formData.get('name'),
            lastName: formData.get('lastName'),
            age: formData.get('age'),
            email: formData.get('email'),
            roles: roles
        };

        console.log("Creating user: ", createdUser);

        const response = await fetch('http://localhost:8080/api/admin/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(createdUser)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        pageState = ADMIN;
        printPage();
        return "User was created";
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
}

async function printAdminsPage() {
    try {
        clearUsersTableContainer();
        // @ts-ignore
        adminSideButton.classList.add("active");
        // @ts-ignore
        userSideButton.classList.remove("active");
        // @ts-ignore
        const users = await fetchUsers();
        console.log('Fetched users:', users);


        const roles = await fetchRoles();
        console.log('Fetched roles:', roles);

        const tableHead = document.createElement('div');
        tableHead.classList.add("mt-3");
        tableHead.id="users-table-container";
        tableHead.innerHTML= `<h5>All users</h5>
            <table class="table table-bordered" id="users-table">
                <thead class="thead-light">
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`;
        // @ts-ignore
        mainContent.appendChild(tableHead);

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
