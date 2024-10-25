def mostrar_menu():
    rol=0
    print("*** Seleccione su rol ***")
    print("1. Administrador")
    print("2. Docente")
    print("3. Estudiante")
    rol = input("Seleccione el rol ")
    print("Su rol es: ",rol)

def main():
    mostrar_menu()
    
if __name__ == "__main__":
    main()