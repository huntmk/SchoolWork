X=imread('Lihua.jpg');
sz = size(X);

prompt = "What is the k value?";
k = input(prompt);

numofexec = 1;
newB = zeros([sz(1),sz(2),numofexec]);

for iter =  1:numofexec
    B = reshape(X,[sz(1)*sz(2),sz(3)]);
    [idx,c] = kmeans(double(B),k);
    [~,pt] = sort(sum(c,2));
    idx_sorted = zeros(size(idx));
    for i = 1:size(idx, 1)
        for  j = 1:size(pt,1)
           if pt(j) == idx(i)
               idx_sorted(i) = j;
           end
        end
    end
    
    newB(:,:,iter) = reshape(idx_sorted,[sz(1),sz(2)]);
end

finalB = mode(newB, 3);

figure();
image(finalB,'CDataMapping','scaled');

clearvars;