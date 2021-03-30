function [zr]=G(c,z);
zr = z(2)-c(1)-c(2)*z(1)-c(3)*z(1)^2;
endfunction
x = [1 1.25 1.5 1.75 2 2.25 2.5 2.75 3];
y = [5.21 4.196 3.759 3.672 4.592 4.621 5.758 7.173 9.269];
z = [x;y];
c = [0;0;0];
[a,err]=datafit(G,z,c)
plot2d(x,y,-4);
t=1:0.01:3;
Ptc=a(1)+a(2)*t+a(3)*t^2;
plot2d(t,Ptc);
