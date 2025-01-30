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
    roles: Role[];
    authorities: Role[];
    stringRoles: string;
    enabled: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
    accountNonExpired: boolean;
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

async function displayUsers() {
    try {
        const users = await fetchUsers();
        console.log('Fetched users:', users);

        // Get the container element
        const container = document.getElementById('users-table-container');

        if (container) {
            // Create a table element
            const table = document.getElementById('users-table');

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
                editTd.innerHTML = `
        <button type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#editUserModal${user.id}">
            Edit
        </button>
        <div class="modal fade" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true" id="editUserModal${user.id}">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="/admin/update" method="post">
                            <input type="hidden" name="id" value="${user.id}">
                            <div class="form-group">
                                <label>First Name</label>
                                <input value="${user.name}" type="text" class="form-control" name="name">
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
                                <input value="${user.email}" type="email" class="form-control" name="email">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-primary" value="Update">
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
        <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#deleteUserModal${user.id}">
            Delete
        </button>
        <div class="modal fade" tabindex="-1" aria-labelledby="deleteUserModalLabel" aria-hidden="true" id="deleteUserModal${user.id}">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Delete User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="/admin/delete" method="post">
                            <input type="hidden" name="id" value="${user.id}">
                            <p>Are you sure you want to delete user ${user.name}?</p>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-danger" value="Delete">
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

// Call the function to display users
displayUsers();