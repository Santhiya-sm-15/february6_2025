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
    public int largestIsland(int[][] grid) {
        int n=grid.length,m=grid[0].length,i,j;
        Disjoint d=new Disjoint(n*m);
        int[][] dir={{0,-1},{-1,0},{0,1},{1,0}};
        for(i=0;i<n;i++)
        {
            for(j=0;j<m;j++)
            {
                if(grid[i][j]==1)
                {
                    for(int[] di:dir)
                    {
                        int nr=i+di[0];
                        int nc=j+di[1];
                        if(nr>=0 && nr<n && nc>=0 && nc<m && grid[nr][nc]==1)
                        {
                            int nn=i*m+j;
                            int an=nr*m+nc;
                            if(d.find(nn)!=d.find(an))
                                d.union(nn,an);
                        }
                    }
                }
            }
        }
        int max=0;
        for(i=0;i<n;i++)
        {
            for(j=0;j<m;j++)
            {
                if(grid[i][j]==1)
                    continue;
                int size=1;
                Set<Integer> s=new HashSet<>();
                for(int[] di:dir)
                {
                    int nr=i+di[0];
                    int nc=j+di[1];
                    if(nr>=0 && nr<n && nc>=0 && nc<m && grid[nr][nc]==1)
                    {
                        int nn=i*m+j;
                        int an=nr*m+nc;
                        int p=d.find(an);
                        if(!s.contains(p))
                        {
                            s.add(p);
                            size+=d.size[p];
                        }
                    }
                }
                max=Math.max(max,size);
            }
        }
        for(i=0;i<n*m;i++)
            max=Math.max(max,d.size[i]);
        return max;
    }
}