import xmlrpc.client
from prettytable import PrettyTable

from debugpy.common.timestamp import reset

# Connect to the RPC server
proxy = xmlrpc.client.ServerProxy("http://localhost:8000/", allow_none=True)

num  = 0

# Call the remote function
while num != 3:
    num = int(input("1. Register for exam\n2. View Exams\n3. Quit:\n"))
    if num==1:
        cn = input("Enter course Name to be registered: ")
        result = proxy.register(cn)
        print("Result from server:", result)
    else:
        res = proxy.schedule(1)
        print(res)

print("Quit")
