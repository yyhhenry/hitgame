#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <ctime>
using namespace std;

int n,m,k,a[200][200];

int main(){
	freopen("0.hg","w",stdout);
	srand((int)time(NULL));
	k=rand()%10+1;
	n=30;m=90;
	for(int i=1;i<=n;i++){
		for(int j=1;j<=m;j++){
			if(rand()%29==0){
				a[i][j]=2;
			}else{
				if(rand()%11==0){
					a[i][j]=1;
				}else{
					a[i][j]=0;
				}
			}
		}
	}
	cout<<n<<" "<<m<<" "<<k<<" "<<endl;a[n/2][m]=0;
	for(int i=1;i<n;i++){
		putchar('0');
		putchar(' ');
		for(int j=2;j<=m;j++){
			cout<<a[i][j]<<" ";
		}
		cout<<endl;
	}
	printf("1");
	for(int i=2;i<=m;i++)printf(" 2");
	printf("\n%d %d %d %d",n-1,1,n/2,m);
	return 0;
}
