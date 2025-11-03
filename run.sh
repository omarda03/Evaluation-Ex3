#!/bin/bash

# Script pour compiler et exécuter le projet de gestion de l'état civil

echo "=== Compilation du projet ==="
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Erreur lors de la compilation!"
    exit 1
fi

echo ""
echo "=== Exécution du programme de test ==="
mvn exec:java -Dexec.mainClass="ma.projet.test.Test" -Dexec.args=""

echo ""
echo "=== Terminé ==="
