#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<algorithm>
using namespace std;
class cpuschedule
{
    int n;
    int burstTime[20];
    float totalWeightingTime=0.0f;
    float averageWaitingTime=0.0f;
    float A[10];
    float waitingTime[10];
    float currentTime;
    public:
        void getData();
        void fcfs();
        void sjf();
        void sjfP();
        void sjfNp();
        void roundRobin();
        void priority();
};
void cpuschedule::getData()
{
    cout<<"\nEnter the no of processes:";
    cin>>n;
    for(int i=1;i<=n;i++)
    {
        cout<<"\nEnter the BurstTime for process p"<<i<<"=";
        cin>>burstTime[i];
    }
}
void cpuschedule::fcfs()
{
    int B[10];
    totalWeightingTime=0.0;
    for(int i=1;i<=n;i++)
    {
        B[i]=burstTime[i];
        cout<<"\nBurst time for process p"<<i<<"=";
        cout<<B[i];
    }
    waitingTime[1]=0;
    for(int i=2;i<=n;i++)
    {
        waitingTime[i]=B[i-1]+waitingTime[i-1];
    }
    for(int i=1;i<=n;i++)
    {
        totalWeightingTime=totalWeightingTime+waitingTime[i];
    }
    averageWaitingTime=totalWeightingTime/n;
    cout<<"\n\nTotal weighting time="<<totalWeightingTime;
    cout<<"\n\nAverage weighting time"<<averageWaitingTime<<""<<endl;
}
void cpuschedule::sjf()
{
    int temp;
    int B[10];
    for(int i=1;i<=n;i++)
    {
        B[i]=burstTime[i];
        cout<<"\nBurst time for process p"<<i<<"=";
        cout<<B[i];
    }
    sort(B,B+(sizeof(B)/sizeof(B[0])));
    // for(int i=n;i>=1;i--)
    // {
    //     for(int j=1;j<=n;j++)
    //     {
    //         if(B[j-1]>B[j])
    //         {
    //             temp=B[j-1];
    //             B[j-1]=B[j];
    //             B[j]=temp;
    //         }
    //     }
    // }
    waitingTime[1]=0;
    for(int i=2;i<=n;i++)
    {
        waitingTime[i]=B[i-1]+waitingTime[i-1];
    }
    for(int i=1;i<=n;i++)
    {
        totalWeightingTime=totalWeightingTime+waitingTime[i];
    }
    averageWaitingTime=totalWeightingTime/n;
    cout<<"\nTotal weighting time-"<<totalWeightingTime;
    cout<<"\nAverage weighting time="<<averageWaitingTime<<"";
}
void cpuschedule::sjfNp()
{
    int i;
    int B[10];
    int totalTime=0;
    int temp;
    int j;
    char s[10];
    float A[10];
    float temp1;
    float t;//currentTime
    totalWeightingTime=0.0;
    currentTime=0.0;
    for(int i=1;i<=n;i++)
    {
        B[i]=burstTime[i];
        cout<<"\nBurst time for process p"<<i<<"=";
        cout<<B[i];
        s[i]='T';
        totalTime=totalTime+B[i];
        cout<<"\nEnter the Arrival time for"<<i<<"th process=";
        cin>>A[i];
    }
    for(int i=n;i>=1;i--)
    {
        for(int j=3;j<=n;j++)
        {
            if(B[j-1]>B[j])
            {
                temp=B[j-1];
                temp1=A[j-1];
                B[j-1]=B[j];
                A[j-1]=A[j];
                B[j]=temp;
                A[j]=temp1;
            }
        }
    }
    for(int i=1;i<=n;i++)
    {
        cout<<"p"<<i<<" "<<B[i]<<" "<<A[i];
    }
    waitingTime[1]=0;
    currentTime=currentTime+B[1];
    t=currentTime;
    s[1]='F';
    while(currentTime<totalTime)
    {
        i=2;
        while(i<=n)
        {
            if(s[i]=='T'&&A[i]<=t)
            {
                waitingTime[i]=currentTime;
                cout<<"WT"<<i<<"="<<waitingTime[i];
                s[i]='F';
                currentTime=currentTime+B[i];
                t=currentTime;
                i=2;
            }
            else
            {
                i++;
            }
        }
    }
    for(int i=1;i<=n;i++)
    {
        cout<<"waiting time"<<i<<"=="<<waitingTime[i];
    }
    for(int i=1;i<=n;i++)
    {
        totalWeightingTime=totalWeightingTime+(waitingTime[i]-A[i]);
    }
    averageWaitingTime=totalWeightingTime/n;
    cout<<"\nTotal weighting time="<<totalWeightingTime<<""<<endl;
    cout<<"\nAverage weighting time"<<averageWaitingTime<<""<<endl;
}
void cpuschedule::priority()
{
    int i;
    int B[10];
    int P[10];
    int j;
    currentTime=0.0;
    int max;
    totalWeightingTime=0.0;
    max=1;
    for(int i=1;i<=n;i++)
    {
        B[i]=burstTime[i];
        cout<<"\nBurst time for process p"<<i<<"= ";
        cout<<B[i];
        cout<<"\nEnter the priority for process p"<<i<<"= ";
        cin>>P[i];
        if(max<P[i])
        {
            max=P[i];
        }
    }
    j=1;
    while(j<=max)
    {
        i=1;
        while(i<=n)
        {
            if(P[i]==j)
            {
                waitingTime[i]=currentTime;
                currentTime=currentTime+B[i];
            }
            i++;
        }
        j++;
    }
    for(int i=1;i<=n;i++)
    {
        totalWeightingTime=totalWeightingTime+waitingTime[i];
    }
    averageWaitingTime=totalWeightingTime/n;
    cout<<"\nTotal weighting time="<<totalWeightingTime<<""<<endl;
    cout<<"\nAverage weighting time"<<averageWaitingTime<<""<<endl;
}
void cpuschedule::sjfP()
{
    int m;
    int waitingTime[10];
    int k;
    int B[10];
    int A[10];
    int totalTime=0;
    int waitintgTime[10];
    int temp;
    char S[20];
    char start[20];
    int max=0;
    int time=0;
    int min;
    float totalWaitingTime=0.0;
    float averageWaitingTime;
    for(int i=1;i<=n;i++)
    {
        B[i]=burstTime[i];
        cout<<"\nBurst time for process p"<<i<<"= "<<B[i];
        if(B[i]>max)
        {
            max=B[i];
        }
        waitingTime[i]=0;
        S[i]='T';
        start[i]='F';
        totalTime=totalTime+B[i];
        cout<<"\nEnter the Arrival time for "<<i<<"th process= ";
        cin>>A[i];
        if(A[i]>time)
        {
            time=A[i];
        }
    }
    int currentTime=0;
    int flag=0;
    int t=0;
    int i=1;
    int j=1;
    while(t<time)
    {
        if(A[i]<=t&&B[i]!=0)
        {
            if(flag==0)
            {
                waitingTime[i]=waitingTime[i]+currentTime;
                cout<<"waitingTime["<<i<<"]="<<waitingTime[i];
            }
            B[i]=B[i]-1;
            if(B[i]==0)
            {
                S[i]='F';
            }
            start[i]='T';
            t++;
            currentTime=currentTime+1;
            if(S[i]!='F')
            {
                j=1;
                flag=1;
                while(j<=n&&flag!=0)
                {
                    if(S[j]!='F'&&B[i]>B[j]&&A[j]<=t&&i!=j)
                    {
                        flag=0;
                        waitingTime[i]=waitingTime[i]-currentTime;
                        i=j;
                    }
                    else
                    {
                        flag=1;
                    }
                    j++;
                }
            }
            else
            {
                i++;
                j=1;
                while(A[j]<=t&&j<=n)
                {
                    if(B[i]>B[j]&&S[j]!='F')
                    {
                        flag=0;
                        i=j;
                    }
                    j++;
                }
            }
        }
        else if(flag==0)
        {
            i++;
        }
    }
    cout<<"\nPrinting remaing burst time";
    for(int i=1;i<=n;i++)
    {
        cout<<"B["<<i<<"]="<<B[i];
    }
    cout<<"";
    while(currentTime<totalTime)
    {
        min=max+1;
        i=1;
        while(i<=n)
        {
            if(min>B[i]&&S[i]=='T')
            {
                min=B[i];
                j=i;
            }
            i++;
        }
        i=j;
        if(currentTime==time&&start[i]=='T')
        {
            currentTime=currentTime+B[i];
            S[i]='F';
        }
        else
        {
            waitingTime[i]=waitingTime[i]+currentTime;
            currentTime=currentTime+B[i];
            S[i]='F';
        }
    }
    cout<<"\nWeight info";
    for(int i=1;i<=n;i++)
    {
        cout<<"WT["<<i<<"]="<<waitingTime[i];
    }
    cout<<"\nafter subrracting arrival time";
    for(int i=1;i<=n;i++)
    {
        waitingTime[i]=waitingTime[i]-A[i];
        cout<<"WT["<<i<<"]="<<waitingTime[i];
    }
    for(int i=1;i<=n;i++)
    {
        totalWaitingTime=totalWaitingTime+waitingTime[i];
    }
    averageWaitingTime=totalWaitingTime/n;
    cout<<"\nAverage weighting time="<<averageWaitingTime<<endl;
}
void cpuschedule::roundRobin()
{
    int i;
    int j;
    int tq;
    int k;
    int B[10];
    int Rrobin[10][10];
    int count[10];
    int max=0;
    int m;
    totalWeightingTime=0.0;
    for(int i=1;i<=n;i++)
    {
        B[i]=burstTime[i];
        cout<<"\nBurst time for process p"<<i<<"=";
        cout<<B[i];
        if(max<B[i])
        {
            max=B[i];
        }
        waitingTime[i]=0;
    }
    cout<<"\nEnter the time quantum=";
    cin>>tq;
    //to find the dimension of the robin array
    m=max/tq+1;
    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=m;j++)
        {
            Rrobin[i][j]=0;
        }
    }
    i=1;
    while(i<=n)
    {
        j=1;
        while(B[i]>0)
        {
            if(B[i]>=tq)
            {
                B[i]=B[i]-tq;
                Rrobin[i][j]=tq;
                j++;
            }
            else
            {
                Rrobin[i][j]=B[i];
                B[i]=0;
                j++;
            }
        }
        count[i]=j-1;
        i++;
    }
    cout<<"Display";
    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=m;j++)
        {
            cout<<"Rr["<<i<<","<<j<<"]="<<Rrobin[i][j];
            cout<<" ";
        }
    }
    int x=1;
    i=1;
    while(x<=n)
    {
        for(int a=1;a<x;a++)
        {
            waitingTime[x]=waitingTime[x]+Rrobin[a][i];
        }
        i=1;
        int z=x;
        j=count[z];
        k=1;
        while(k<=j-1)
        {
            if(i==n+1)
            {
                i=1;
                k++;
            }
            else
            {
                if(i!=z)
                {
                    waitingTime[z]=waitingTime[z]+Rrobin[i][k];
                }
                i++;
            }
        }
        x++;
    }
    for(int i=1;i<=n;i++)
    {
        cout<<"\nWeighting Tme for process p"<<i<<"="<<waitingTime[i];
    }
    for(int i=1;i<=n;i++)
    {
        totalWeightingTime=totalWeightingTime+waitingTime[i];
    }
    averageWaitingTime=totalWeightingTime/n;
    cout<<"\nTotal weighting time="<<totalWeightingTime<<endl;
    cout<<"\nAverage Weighting time="<<averageWaitingTime<<""<<endl;
}
int main()
{
    int ch;
    int cho;
    cpuschedule c;
    do
    {
        cout<<"1.Getting BurstTime"<<endl;
        cout<<"2.FirstComeFirstServed"<<endl;
        cout<<"3.ShortestJobFirst"<<endl;
        cout<<"4.RoundRobin"<<endl;
        cout<<"5.Priority"<<endl;
        cout<<"6.EXIT"<<endl;
        cout<<"Enter your choice: ";
        cin>>ch;
        switch(ch)
        {
        case 1:
            c.getData();
            break;
        case 2:
            cout<<"\nFIRST COME FIRST SERVED SCHEDULING"<<endl;
            c.fcfs();
            break;
        case 3:
            cout<<"\nSHORTEST JOB FIRST SCHEDULING"<<endl;
            do
            {
                cout<<"\n1.SJF-Normal"<<endl;
                cout<<"2.SJF-Preemptive"<<endl;
                cout<<"3.SJF-NonPreemptive"<<endl;
                cout<<"Enter your choice: ";
                cin>>cho;
                switch(cho)
                {
                case 1:
                    c.sjf();
                    break;
                case 2:
                    c.sjfP();
                    break;
                case 3:
                    c.sjfNp();
                    break;
                }
            }
            while(cho<=3);
            break;
        case 4:
            cout<<"\nROUND ROBIN SCHEDULING"<<endl;
            c.roundRobin();
            break;
        case 5:
            cout<<"\nPRIORITY SCHEDULING"<<endl;
            c.priority();
            break;
        case 6:
            break;
        }
    }
    while(ch<=5);
}
