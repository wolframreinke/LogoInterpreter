#! /bin/bash

function print_help
{
	echo -e "$0 [-h] -o <output_dir>"
	echo -e ""
	echo -e "Cleanup script which copies the LogoInterpreter workspaces and"
	echo -e "removes all files from it, which are not released together with"
	echo -e "the project."
	echo -e ""
	echo -e "OPTIONS:"
	echo -e "\t-h\tPrints this help message."
	echo -e "\t-o\tSets the output directory. The eclipse project"
	echo -e "\t  \t'LogoInterpter' will be copied to this directory."
	echo -e ""
}

while getopts o:h INPUT
do	
	case $INPUT
	in
		o)	OUT_DIR=$OPTARG
			;;
		h)	print_help
			exit 0
			;;
		\?)	print_help
			exit 1
			;;
	esac
done

if [ -z "$OUT_DIR" ]
then
	echo "No output directory specified. Execution terminated." 1>&2
	echo "" 1>&2
	exit 1	
fi

echo "Copying project to $OUT_DIR..."
mkdir $OUT_DIR > /dev/null 2>&1
cp -r $HOME/git/LogoInterpreter/LogoInterpreter $OUT_DIR/LogoInterpreter \
	&& 	{	
			echo "Done."
			echo "" 1>&2
	}|| {
			echo "Failure." 1>&2
			echo "" 1>&2
			exit 1
		}
		
ORIGIN=$PWD
cd $OUT_DIR/LogoInterpreter \
	||	{
			echo "Could not enter $OUT_DIR/LogoInterpreter." 1>&2
			echo "" 1>&2
			exit 1
		}
		
echo "Removing files in $OUT_DIR/LogoInterpreter..."
rm *.* *_*
rm -rf testing

echo "Removing files in $OUT_DIR/LogoInterpreter/docs..."
cd docs
rm general.uxf parsing_v1.0.uxf variables_v1.0.uxf

echo "Removing $OUT_DIR/LogoInterpreter/bin..."
cd ..
rm -rf bin

echo "Cleaning up $OUT_DIR/LogoInterpreter/.classpath..."

rm .tmp > /dev/null 2>&1
touch .tmp

while IFS= read LINE
do
	if [ -z "$(echo $LINE | grep -Pi 'path=\"testing\"\/>$')" ]
	then
		echo "$LINE" >> .tmp
	fi 
done < .classpath

mv .tmp .classpath

echo "Removing .gitignore..."
rm .gitignore

cd $ORIGIN
echo ""
echo "Done."
echo ""










