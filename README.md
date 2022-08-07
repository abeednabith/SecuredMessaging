A client server program using socket programming. The communication is encrypted using a private key or secret key cryptogrpahy using AES algorithm.

Sender: generator a random number, then encrypt the random number by a secret key with message digest(MD5 hashing) together send to receiver.

Receiver: decrypt the received message and generate an new message digest(MD5 hashing), and compare with original one to make sure there was no issues.
