node {
   stage('Clone') {
      checkout scm
   }

   stage('Build') {
       sh "mvn clean install -U"
   }
}
