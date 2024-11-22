
drop table EMP;
drop table DEPT;
drop table SALGRADE;


create table DEPT( DEPTNO	INTEGER(2) NOT NULL,
		   DNAME	VARCHAR(14),
		   LOC		VARCHAR(13),
		   PRIMARY KEY (DEPTNO),
		   CONSTRAINT DEPTNO_CK CHECK (DEPTNO BETWEEN 10 AND 99) );


create table EMP ( EMPNO	INTEGER(4) NOT NULL,
		   ENAME	VARCHAR(10) NOT NULL,
		   JOB		VARCHAR(9),
		   MGR		INTEGER(4),
		   HIREDATE	DATE,
		   SAL		FLOAT,
		   COMM		FLOAT,
		   DEPTNO	INTEGER(2) NOT NULL,
		   PRIMARY KEY (EMPNO),
		   FOREIGN KEY (MGR) REFERENCES EMP (EMPNO),
		   FOREIGN KEY (DEPTNO) REFERENCES DEPT (DEPTNO) );


create table
	 SALGRADE( GRADE	INTEGER(1) NOT NULL,
		   LOSAL	FLOAT,
		   HISAL	FLOAT,
		   PRIMARY KEY (GRADE) );


insert into DEPT values ( 10, 'ACCOUNTING', 'NEW YORK' );
insert into DEPT values ( 20, 'RESEARCH',   'DALLAS'   );
insert into DEPT values ( 30, 'SALES',      'CHICAGO'  );
insert into DEPT values ( 40, 'OPERATIONS', 'BOSTON'   );


insert into EMP values ( 7839, 'KING',   'PRESIDENT', null, '1981-11-17', 5000.00,    null, 10 );
insert into EMP values ( 7782, 'CLARK',  'MANAGER',   7839, '1981-06-09', 2450.00,    null, 10 );
insert into EMP values ( 7934, 'MILLER', 'CLERK',     7782, '1983-01-12', 1300.00,    null, 10 );
insert into EMP values ( 7566, 'JONES',  'MANAGER',   7839, '1981-04-02', 2975.00,    null, 20 );
insert into EMP values ( 7902, 'FORD',   'ANALYST',   7566, '1981-12-03', 3000.00,    null, 20 );
insert into EMP values ( 7369, 'SMITH',  'CLERK',     7902, '1980-12-17',  800.00,    null, 20 );
insert into EMP values ( 7788, 'SCOTT',  'ANALYST',   7566, '1982-11-05', 3000.00,    null, 20 );
insert into EMP values ( 7876, 'ADAMS',  'CLERK',     7788, '1982-12-09', 1100.00,    null, 20 );
insert into EMP values ( 7698, 'BLAKE',  'MANAGER',   7839, '1981-05-01', 2850.00,    null, 30 );
insert into EMP values ( 7499, 'ALLEN',  'SALESMAN',  7698, '1981-02-20', 1600.00,  300.00, 30 );
insert into EMP values ( 7654, 'MARTIN', 'SALESMAN',  7698, '1981-09-28', 1250.00, 1400.00, 30 );
insert into EMP values ( 7900, 'JAMES',  'CLERK',     7698, '1981-12-03',  950.00,    null, 30 );
insert into EMP values ( 7844, 'TURNER', 'SALESMAN',  7698, '1981-09-08', 1500.00,    null, 30 );
insert into EMP values ( 7521, 'WARD',   'SALESMAN',  7698, '1981-02-22', 1250.00,  500.00, 30 );


insert into SALGRADE values ( 1,    0.00, 1199.00 );
insert into SALGRADE values ( 2, 1200.00, 1399.00 );
insert into SALGRADE values ( 3, 1400.00, 1999.00 );
insert into SALGRADE values ( 4, 2000.00, 3999.00 );
insert into SALGRADE values ( 5, 4000.00, 5000.00 );
