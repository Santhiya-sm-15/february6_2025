class Disjoint
{
    int[] parent;
    int[] size;
    public Disjoint(int n)
    {
        parent=new int[n];
        size=new int[n];
        for(int i=0;i<n;i++)
        {
            parent[i]=i;
            size[i]=1;
        }
    }
    public int find(int x)
    {
        if(parent[x]==x)
            return x;
        return parent[x]=find(parent[x]);
    }
    public void union(int u,int v)
    {
        int pu=find(u);
        int pv=find(v);
        if(pu==pv)
            return;
        if(size[pu]<size[pv])
        {
            parent[pu]=pv;
            size[pv]+=size[pu];
        }
        else
        {
            parent[pv]=pu;
            size[pu]+=size[pv];
        }
    }
}
class Solution {
    public int removeStones(int[][] stones) {
        int n=0,m=0,i;
        for(int[] x:stones)
        {
            n=Math.max(n,x[0]);
            m=Math.max(m,x[1]);
        }
        Disjoint d=new Disjoint(n+m+2);
        for(int[] x:stones)
        {
            int r=x[0];
            int c=x[1]+n+1;
            d.union(r,c);
        }
        int cmp=0;
        for(i=0;i<n+m+2;i++)
        {
            if(d.find(i)==i && d.size[i]>1)
                cmp++;
        }
        return stones.length-cmp;
    }
}
