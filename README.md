1. Fare la build del bundle con gradle
2. Lanciare il comando per creare l'immagine docker :
   ./build.sh --from-local-dist ~/Downloads/apache-karaf-4.2.1.tar.gz --image-name "karaf:4.2.1"
3. Lanciare il comando docker-compose up per far partire un daemon di karaf:
   docker-compose up

Reference: https://github.com/apache/karaf/tree/master/assemblies/docker
