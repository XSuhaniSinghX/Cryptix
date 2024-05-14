# Cryptix 🛡️

Cryptix is a simple Java application for performing cryptographic operations including file encryption/decryption, file hashing, and digital signatures.

## Features

- **File Encryption/Decryption**: Encrypt and decrypt files using the AES algorithm.
- **Key Management**: Generate, save, and load AES keys securely.
- **File Hashing**: Generate SHA-256 hash of files.
- **Digital Signatures**: Sign files with digital signatures and verify signatures.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) installed on your machine.
- Java IDE such as IntelliJ IDEA or Eclipse.

### Installation

1. Clone the repository to your local machine using Git:
git clone https://github.com/XSuhsniSinghX/Cryptix.git


2. Open the project in your Java IDE.

3. Build and run the `Main.java` class to launch the application.

## Usage

### Windows

1. Launch the Cryptix application.
2. Use the buttons to perform various cryptographic operations:
   - **Browse**: Select a file to operate on.
   - **Encrypt/Decrypt**: Encrypt or decrypt the selected file.
   - **Generate Key**: Generate a new AES key.
   - **Save/Load Key**: Save or load an AES key from a file.
   - **Generate Hash**: Generate a SHA-256 hash of the selected file.
   - **Sign/Verify**: Sign a file with a digital signature or verify a signature.

### Linux

1. Open a terminal window.

2. Navigate to the directory containing your compiled `.class` files.

3. Run the following command to execute the Cryptix:
   
~~~
java Main [options]
~~~

Replace [options] with the desired command-line options. Here are some examples:

To encrypt a file:

~~~
java Main encrypt /path/to/input/file.txt /path/to/output/encrypted_file.enc keyfile.key
~~~

To decrypt a file:

~~~
java Main decrypt /path/to/input/encrypted_file.enc /path/to/output/decrypted_file.txt keyfile.key
~~~
To generate a hash of a file:
~~~
java Main hash /path/to/input/file.txt
~~~
To sign a file:
~~~
java Main sign /path/to/input/file.txt /path/to/output/signature.sig keyfile.key
~~~
To verify a signature:
~~~
java Main verify /path/to/input/file.txt /path/to/input/signature.sig keyfile.key
~~~
