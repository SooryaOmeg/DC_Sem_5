from xmlrpc.server import SimpleXMLRPCServer
import prettytable
import mysql.connector

connection = mysql.connector.connect(
    host="",
    user="",
    password="",
    database=""
)

if connection.is_connected():
    print("Connected to MySQL database")
cursor = connection.cursor()

create_table_query = ("Create table if not exists scheduled(id varchar(50) primary key, Name  varchar(50), "
                      "Marks int, Student_registered int,Exam_date varchar(50), Start_time varchar(50), Duration int)")

cursor.execute(create_table_query)
connection.commit()

print("Table created successfully!")

arr = [
        ["CS301", "DC-ISE 1", 10, 50, "20/09/2024","19:00",10],
        ["CS201", "DSGT-ISE 1", 20, 70, "21/09/2024","14:30",25],
        ["CS302", "AIML-ISE 1", 10, 25, "19/09/2024","13:45",10],
        ["MTH101", "EM-ISE 1", 30, 100, "22/09/2024","10:30",30],
        ["APSH102", "EC-ISE 1", 20, 0, "20/09/2024","09:30",25],
        ["EE202", "SS-ISE 1", 10, 20, "21/09/2024","11:30",10],
        ["LLC102", "IPDC Eval 1", 50, 50, "22/09/2024","12:30",60],
    ]

insert_query = "insert into scheduled value (%s, %s, %s, %s, %s, %s, %s)"

for i in arr:
    select_query = "select * from scheduled where id=%s"
    val = i[0]
    cursor.execute(select_query, (val,))
    data = cursor.fetchall()
    if not data:
        values = tuple(i)
        cursor.execute(insert_query, values)
        connection.commit()
        print("Record inserted successfully!")



cursor.execute("SELECT * FROM scheduled")
rows = cursor.fetchall()

column_names = [i[0] for i in cursor.description]
table = prettytable.PrettyTable()
table.field_names = column_names

for row in rows:
    table.add_row(row)
# print(table)


def register(exam_id):
    print("Hello")
    cursor.execute("select * from scheduled where id = %s", (exam_id,))
    get_exam = cursor.fetchall()
    if get_exam:
        cursor.execute("update scheduled set Student_registered = Student_registered + 1 where id = %s",
                       (exam_id,))
        connection.commit()
        print("Bye")
        schedule(1)
        return "Registered Successfully!"
    else:
        print("Exam not yet scheduled")
        return "Exam not yet scheduled"

def schedule(number):
    cursor.execute("select * from scheduled")
    r = cursor.fetchall()

    col_name = [i[0] for i in cursor.description]
    tables = prettytable.PrettyTable()
    tables.field_names = col_name

    for _ in r:
        tables.add_row(_)
    print(tables)
    return tables.get_string()




server = SimpleXMLRPCServer(("localhost", 8000), logRequests=True)
server.register_function(register, "register")
server.register_function(schedule, "schedule")# Register the add function to be accessible remotely

print("RPC Server running...")
server.serve_forever()
