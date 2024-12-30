@echo off
clear

# create a bucket
# ======================================================================================================================
read -p "Enter bucketname: " BUCKETNAME

aws s3api create-bucket --bucket "$BUCKETNAME"

echo
echo Bucket "$BUCKETNAME" created!
# ======================================================================================================================

# show list of all buckets
# ======================================================================================================================
echo
echo List of all buckets
aws s3api list-buckets
# ======================================================================================================================

# show list of attached policies to the bucket
# ======================================================================================================================
echo
echo ACL of bucket "$BUCKETNAME"
aws s3api get-bucket-acl --bucket "$BUCKETNAME"
# ======================================================================================================================

# delete the bucket
# ======================================================================================================================
echo
read -p "Press Enter to continue..." </dev/tty

aws s3api delete-bucket --bucket "$BUCKETNAME"
echo
echo Bucket "$BUCKETNAME" deleted!
# ======================================================================================================================
