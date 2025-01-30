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
        const response = await fetch('http://localhost:8080/admin/users', {
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

        // Display users in the UI or process them further
        users.forEach(user => {
            console.log(`User: ${user.name} ${user.lastName}, Email: ${user.email}, Roles: ${user.stringRoles}`);
        });
    } catch (error) {
        console.error('Failed to fetch users:', error);
    }
}

// Call the function to fetch and display users
displayUsers();