# february6_2025
The problems that I solved today

1.Given an array nums of distinct positive integers, return the number of tuples (a, b, c, d) such that a * b = c * d where a, b, c, and d are elements of nums, and a != b != c != d.

Code:
class Solution {
    public int tupleSameProduct(int[] nums) {
        Map<Long,Integer> m=new HashMap<>();
        int i,j;
        for(i=0;i<nums.length;i++)
        {
            for(j=i+1;j<nums.length;j++)
            {
                long pro=nums[i]*nums[j];
                m.put(pro,m.getOrDefault(pro,0)+1);
            }
        }
        int cnt=0;
        for(Map.Entry<Long,Integer> x:m.entrySet())
        {
            int a=x.getValue();
            a=4*(a*(a-1));
            cnt+=(a);
        }
        return cnt;
    }
}

2.You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1. Return the size of the largest island in grid after applying this operation. An island is a 4-directionally connected group of 1s.

Code:
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

3.On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone. A stone can be removed if it shares either the same row or the same column as another stone that has not been removed. Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone, return the largest possible number of stones that can be removed.

Code:
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

4.You are given an n x n integer matrix grid where each value grid[i][j] represents the elevation at that point (i, j). The rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can swim infinite distances in zero time. Of course, you must stay within the boundaries of the grid during your swim. Return the least time until you can reach the bottom right square (n - 1, n - 1) if you start at the top left square (0, 0).

Code:
class Solution {
    public int swimInWater(int[][] grid) {
        int n=grid.length,m=grid[0].length,i,j;
        boolean[][] visited=new boolean[n][m];
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->Integer.compare(a[0],b[0]));
        int[][] dir={{0,-1},{-1,0},{0,1},{1,0}};
        pq.add(new int[]{grid[0][0],0,0});
        int max=grid[0][0];
        while(!pq.isEmpty())
        {
            int[] x=pq.poll();
            int val=x[0];
            int r=x[1];
            int c=x[2];
            if(r==n-1 && c==m-1)
                return Math.max(max,val);
            if(visited[r][c])
                continue;
            visited[r][c]=true;
            max=Math.max(max,val);
            for(int[] d:dir)
            {
                int er=r+d[0];
                int ec=c+d[1];
                if(er>=0 && er<n && ec>=0 && ec<m && !visited[er][ec])
                {
                    pq.add(new int[]{grid[er][ec],er,ec});
                }
            }
        }
        return 0;
    }
}
