A = [5 1 7; -10 -2 1; 0 1 2];
B = [2 4 1; 3 1 0; 7 2 1];
D = 2 * (A - B) * (A * A + B)
K = inv(D)
P = D*K
