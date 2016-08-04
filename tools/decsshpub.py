#!/usr/bin/env python
##-*-coding: utf-8;-*-##
import base64
import sys
import struct


sshpub = sys.stdin.read()
key_type, pem_data, comments = sshpub.split()

decoded = base64.b64decode(pem_data)

data_pairs = list()
while decoded:
    n = struct.unpack('>I', decoded[:4])[0]
    d = decoded[4:4+n]
    decoded = decoded[4+n:]
    data_pairs.append((n, d))
assert len(data_pairs) == 3

alg = data_pairs[0]
exp = data_pairs[1]
mod = data_pairs[2]

e = int(''.join('%02X' % struct.unpack('B', b)[0] for b in exp[1]), 16)
m = int(''.join('%02X' % struct.unpack('B', b)[0] for b in mod[1]), 16)

print 'Comments:', comments
print 'Key Type:', key_type
print 'Algorithm:', alg
print 'e:', e
print 'm:', m
